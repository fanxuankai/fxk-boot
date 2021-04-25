package com.fanxuankai.boot.mqbroker.rabbit.autoconfigure;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.text.StrPool;
import cn.hutool.json.JSONUtil;
import com.fanxuankai.boot.mqbroker.config.MqBrokerProperties;
import com.fanxuankai.boot.mqbroker.consume.AbstractMqConsumer;
import com.fanxuankai.boot.mqbroker.consume.EventListenerRegistry;
import com.fanxuankai.boot.mqbroker.consume.MqConsumer;
import com.fanxuankai.boot.mqbroker.model.Event;
import com.fanxuankai.boot.mqbroker.produce.AbstractMqProducer;
import com.fanxuankai.boot.mqbroker.produce.MqProducer;
import com.fanxuankai.boot.mqbroker.rabbit.MqBrokerRabbitProducer;
import com.fanxuankai.boot.mqbroker.service.MsgSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.Objects;
import java.util.Optional;

/**
 * @author fanxuankai
 */
public class MqBrokerRabbitAutoConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(MqBrokerRabbitAutoConfiguration.class);

    @Bean
    public MessageListenerContainer simpleMessageQueueLister(ConnectionFactory connectionFactory,
                                                             AbstractMqConsumer<Event<String>> mqConsumer,
                                                             AmqpAdmin amqpAdmin,
                                                             MqBrokerProperties mqBrokerProperties) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        // 监听的队列
        container.setQueues(EventListenerRegistry.getAllListenerMetadata()
                .stream()
                .map(o -> new Queue(o.getTopic()))
                .peek(amqpAdmin::declareQueue)
                .toArray(Queue[]::new));
        // 是否重回队列
        container.setDefaultRequeueRejected(false);
        container.setErrorHandler(throwable -> LOGGER.error("消费异常", throwable));
        if (mqBrokerProperties.isManualAcknowledge()) {
            // 手动确认
            container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
            container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
                String json = new String(message.getBody());
                Event<String> event = JSONUtil.toBean(json, new TypeReference<Event<String>>() {
                }, true);
                mqConsumer.accept(event);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            });
        } else {
            // 自动确认
            container.setAcknowledgeMode(AcknowledgeMode.AUTO);
            container.setMessageListener(message -> {
                String json = new String(message.getBody());
                Event<String> event = JSONUtil.toBean(json, new TypeReference<Event<String>>() {
                }, true);
                mqConsumer.accept(event);
            });
        }
        return container;
    }

    @Bean
    @ConditionalOnMissingBean(MqProducer.class)
    public AbstractMqProducer mqProducer(AmqpAdmin amqpAdmin,
                                         RabbitTemplate rabbitTemplate,
                                         RabbitProperties rabbitProperties,
                                         MsgSendService msgSendService,
                                         MqBrokerProperties mqBrokerProperties) {
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            assert correlationData != null;
            String[] split = Objects.requireNonNull(correlationData.getId()).split(StrPool.COMMA);
            String topic = split[0];
            String code = split[1];
            if (ack) {
                msgSendService.success(topic, code);
            } else {
                msgSendService.failure(topic, code, Optional.ofNullable(cause).orElse("nack"));
            }
        });
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String json = new String(message.getBody());
            Event<String> event = JSONUtil.toBean(json, new TypeReference<Event<String>>() {
            }, true);
            String cause = "replyCode: " + replyCode + ", replyText: " + replyText + ", exchange: " + exchange;
            msgSendService.failure(routingKey, event.getKey(), cause);
        });
        return new MqBrokerRabbitProducer(amqpAdmin, rabbitTemplate, rabbitProperties, mqBrokerProperties,
                StrPool.COMMA);
    }

    @Bean
    @ConditionalOnMissingBean(MqConsumer.class)
    public AbstractMqConsumer<Event<String>> mqConsumer() {
        return new AbstractMqConsumer<Event<String>>() {
        };
    }

}
