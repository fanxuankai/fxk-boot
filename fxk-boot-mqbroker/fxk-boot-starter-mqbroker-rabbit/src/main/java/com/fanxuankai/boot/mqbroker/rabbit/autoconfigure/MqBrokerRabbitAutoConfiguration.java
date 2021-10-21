package com.fanxuankai.boot.mqbroker.rabbit.autoconfigure;

import cn.hutool.core.text.StrPool;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fanxuankai.boot.mqbroker.autoconfigure.MqBrokerProperties;
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
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author fanxuankai
 */
public class MqBrokerRabbitAutoConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(MqBrokerRabbitAutoConfiguration.class);

    @Bean
    public MessageListenerContainer simpleMessageQueueLister(ConnectionFactory connectionFactory,
                                                             AbstractMqConsumer<Event<String>> mqConsumer,
                                                             AmqpAdmin amqpAdmin,
                                                             MqBrokerProperties mqBrokerProperties,
                                                             EventListenerRegistry eventListenerRegistry) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        // 监听的队列
        container.setQueues(eventListenerRegistry.getAllListenerMetadata()
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
                Event<String> event = JSON.parseObject(json, new TypeReference<Event<String>>() {
                });
                mqConsumer.accept(event);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            });
        } else {
            // 自动确认
            container.setAcknowledgeMode(AcknowledgeMode.AUTO);
            container.setMessageListener(message -> {
                String json = new String(message.getBody());
                Event<String> event = JSON.parseObject(json, new TypeReference<Event<String>>() {
                });
                mqConsumer.accept(event);
            });
        }
        return container;
    }

    @Bean
    @ConditionalOnMissingBean(MqProducer.class)
    public AbstractMqProducer mqProducer(MsgSendService msgSendService,
                                         AmqpAdmin amqpAdmin,
                                         ConnectionFactory connectionFactory,
                                         RabbitProperties rabbitProperties,
                                         MqBrokerProperties mqBrokerProperties) {
        return new MqBrokerRabbitProducer(msgSendService, amqpAdmin, connectionFactory, rabbitProperties,
                mqBrokerProperties, StrPool.COMMA);
    }

    @Bean
    @ConditionalOnMissingBean(MqConsumer.class)
    public AbstractMqConsumer<Event<String>> mqConsumer() {
        return new AbstractMqConsumer<Event<String>>() {
        };
    }
}
