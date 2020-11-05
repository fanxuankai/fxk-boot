package com.fanxuankai.boot.mqbroker.rabbit.autoconfigure;

import com.fanxuankai.boot.mqbroker.config.MqBrokerProperties;
import com.fanxuankai.boot.mqbroker.consume.AbstractMqConsumer;
import com.fanxuankai.boot.mqbroker.consume.EventListenerRegistry;
import com.fanxuankai.boot.mqbroker.consume.MqConsumer;
import com.fanxuankai.boot.mqbroker.model.Event;
import com.fanxuankai.boot.mqbroker.produce.AbstractMqProducer;
import com.fanxuankai.boot.mqbroker.produce.MqProducer;
import com.fanxuankai.boot.mqbroker.service.MsgSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.ConfirmType;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @author fanxuankai
 */
@Slf4j
public class MqBrokerRabbitAutoConfiguration {

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    @SuppressWarnings("unchecked")
    public MessageListenerContainer simpleMessageQueueLister(ConnectionFactory connectionFactory,
                                                             AbstractMqConsumer<Event<String>> mqConsumer,
                                                             AmqpAdmin amqpAdmin,
                                                             MqBrokerProperties mqBrokerProperties,
                                                             MessageConverter converter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        // 监听的队列
        container.setQueues(EventListenerRegistry.getAllListenerMetadata()
                .stream()
                .map(o -> new Queue(o.getTopic()))
                .peek(amqpAdmin::declareQueue)
                .toArray(Queue[]::new));
        // 是否重回队列
        container.setDefaultRequeueRejected(false);
        container.setErrorHandler(throwable -> log.error("消费异常", throwable));
        if (mqBrokerProperties.isManualAcknowledge()) {
            // 手动确认
            container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
            container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
                Event<String> event = (Event<String>) converter.fromMessage(message);
                mqConsumer.accept(event);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            });
        } else {
            // 自动确认
            container.setAcknowledgeMode(AcknowledgeMode.AUTO);
            container.setMessageListener(message -> {
                Event<String> event = (Event<String>) converter.fromMessage(message);
                mqConsumer.accept(event);
            });
        }
        return container;
    }

    @Bean
    @ConditionalOnMissingBean(MqProducer.class)
    @SuppressWarnings("unchecked")
    public AbstractMqProducer mqProducer(AmqpAdmin amqpAdmin,
                                         RabbitTemplate rabbitTemplate,
                                         RabbitProperties rabbitProperties,
                                         MsgSendService msgSendService,
                                         MqBrokerProperties mqBrokerProperties,
                                         MessageConverter converter) {
        String correlationDataRegex = ",";
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            assert correlationData != null;
            String[] split = Objects.requireNonNull(correlationData.getId()).split(correlationDataRegex);
            String topic = split[0];
            String code = split[1];
            if (ack) {
                msgSendService.success(topic, code);
            } else {
                msgSendService.failure(topic, code, Optional.ofNullable(cause).orElse("nack"));
            }
        });
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            Event<String> event = (Event<String>) converter.fromMessage(message);
            String cause = "replyCode: " + replyCode + ", replyText: " + replyText + ", exchange: " + exchange;
            msgSendService.failure(routingKey, event.getKey(), cause);
        });
        Exchange exchange = new DirectExchange("mqBrokerRabbit.exchange");
        amqpAdmin.declareExchange(exchange);
        final boolean enabledDelayedMessage = Objects.equals(mqBrokerProperties.getEnabledDelayedMessage(),
                Boolean.TRUE);
        Exchange delayedExchange = null;
        if (enabledDelayedMessage) {
            final Map<String, Object> args = new HashMap<>(1);
            args.put("x-delayed-type", "direct");
            delayedExchange = new CustomExchange("mqBrokerRabbit.delayed.exchange", "x-delayed-message",
                    true, false, args);
            amqpAdmin.declareExchange(delayedExchange);
        }
        final Exchange fExchange = exchange;
        final Exchange fDelayedExchange = delayedExchange;
        return new AbstractMqProducer() {
            private final Set<String> queueCache = new HashSet<>();

            @Override
            public boolean isPublisherCallback() {
                ConfirmType publisherConfirmType = rabbitProperties.getPublisherConfirmType();
                return publisherConfirmType != null
                        && publisherConfirmType != ConfirmType.NONE
                        && rabbitProperties.isPublisherReturns();
            }

            @Override
            public void accept(Event<String> event) {
                if (!queueCache.contains(event.getName())) {
                    Queue queue = new Queue(event.getName());
                    amqpAdmin.declareQueue(queue);
                    amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(queue.getName()).noargs());
                    if (enabledDelayedMessage) {
                        amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(fDelayedExchange).with(queue.getName()).noargs());
                    }
                    queueCache.add(event.getName());
                }
                Optional<LocalDateTime> effectiveTimeOptional = Optional.ofNullable(event.getEffectTime())
                        .map(date -> LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
                long millis;
                if (effectiveTimeOptional.isPresent()
                        && (millis = Duration.between(LocalDateTime.now(), effectiveTimeOptional.get()).toMillis()) > 0) {
                    rabbitTemplate.convertAndSend(fDelayedExchange.getName(),
                            event.getName(), event, message -> {
                                message.getMessageProperties().setHeader("x-delay", millis);
                                return message;
                            }, new CorrelationData(event.getName() + correlationDataRegex + event.getKey()));
                } else {
                    rabbitTemplate.convertAndSend(fExchange.getName(), event.getName(), event,
                            new CorrelationData(event.getName() + correlationDataRegex + event.getKey()));
                }
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(MqConsumer.class)
    public AbstractMqConsumer<Event<String>> mqConsumer() {
        return new AbstractMqConsumer<Event<String>>() {
        };
    }

}
