package com.fanxuankai.boot.mqbroker.rabbit;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.json.JSONUtil;
import com.fanxuankai.boot.mqbroker.autoconfigure.MqBrokerProperties;
import com.fanxuankai.boot.mqbroker.model.Event;
import com.fanxuankai.boot.mqbroker.produce.AbstractMqProducer;
import com.fanxuankai.boot.mqbroker.service.MsgSendService;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @author fanxuankai
 */
public class MqBrokerRabbitProducer extends AbstractMqProducer {
    private final Set<String> queueCache = new HashSet<>();
    private final MsgSendService msgSendService;
    private final AmqpAdmin amqpAdmin;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitProperties rabbitProperties;
    private final MqBrokerProperties mqBrokerProperties;
    private final String correlationDataRegex;
    private Exchange exchange;
    private Exchange delayedExchange;
    private boolean enabledDelayedMessage;

    public MqBrokerRabbitProducer(MsgSendService msgSendService,
                                  AmqpAdmin amqpAdmin,
                                  ConnectionFactory connectionFactory,
                                  RabbitProperties rabbitProperties,
                                  MqBrokerProperties mqBrokerProperties,
                                  String correlationDataRegex) {
        this.msgSendService = msgSendService;
        this.amqpAdmin = amqpAdmin;
        this.rabbitProperties = rabbitProperties;
        this.mqBrokerProperties = mqBrokerProperties;
        this.correlationDataRegex = correlationDataRegex;
        this.rabbitTemplate = new RabbitTemplate(connectionFactory);
        init();
    }

    private void init() {
        this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
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
        this.rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String json = new String(message.getBody());
            Event<String> event = JSONUtil.toBean(json, new TypeReference<Event<String>>() {
            }, true);
            String cause = "replyCode: " + replyCode + ", replyText: " + replyText + ", exchange: " + exchange;
            msgSendService.failure(routingKey, event.getKey(), cause);
        });
        Exchange exchange = new DirectExchange("mqBrokerRabbit.exchange");
        amqpAdmin.declareExchange(exchange);
        enabledDelayedMessage = Objects.equals(mqBrokerProperties.getEnabledDelayedMessage(), Boolean.FALSE);
        Exchange delayedExchange = null;
        if (enabledDelayedMessage) {
            Map<String, Object> args = MapUtil.of("x-delayed-type", "direct");
            delayedExchange = new CustomExchange("mqBrokerRabbit.delayed.exchange", "x-delayed-message",
                    true, false, args);
            amqpAdmin.declareExchange(delayedExchange);
        }
        this.exchange = exchange;
        this.delayedExchange = delayedExchange;
    }

    @Override
    public boolean isPublisherCallback() {
        CachingConnectionFactory.ConfirmType publisherConfirmType = rabbitProperties.getPublisherConfirmType();
        return publisherConfirmType != null
                && publisherConfirmType != CachingConnectionFactory.ConfirmType.NONE
                && rabbitProperties.isPublisherReturns();
    }

    @Override
    public void accept(Event<String> event) {
        if (!queueCache.contains(event.getName())) {
            Queue queue = new Queue(event.getName());
            amqpAdmin.declareQueue(queue);
            amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(queue.getName()).noargs());
            if (enabledDelayedMessage) {
                amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(delayedExchange).with(queue.getName()).noargs());
            }
            queueCache.add(event.getName());
        }
        Optional<LocalDateTime> effectiveTimeOptional = Optional.ofNullable(event.getEffectTime())
                .map(date -> LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
        long millis;
        if (effectiveTimeOptional.isPresent()
                && (millis = Duration.between(LocalDateTime.now(), effectiveTimeOptional.get()).toMillis()) > 0) {
            rabbitTemplate.convertAndSend(delayedExchange.getName(),
                    event.getName(), convertMessage(event), message -> {
                        message.getMessageProperties().setHeader("x-delay", millis);
                        return message;
                    }, new CorrelationData(event.getName() + correlationDataRegex + event.getKey()));
        } else {
            rabbitTemplate.convertAndSend(exchange.getName(), event.getName(), convertMessage(event),
                    new CorrelationData(event.getName() + correlationDataRegex + event.getKey()));
        }
    }

    private Message convertMessage(Object object) {
        return MessageBuilder
                .withBody(JSONUtil.toJsonStr(object).getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
    }
}
