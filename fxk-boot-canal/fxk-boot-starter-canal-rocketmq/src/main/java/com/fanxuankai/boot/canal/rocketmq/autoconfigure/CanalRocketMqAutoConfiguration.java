package com.fanxuankai.boot.canal.rocketmq.autoconfigure;

import com.fanxuankai.boot.canal.mq.config.autoconfigure.CanalMqProperties;
import com.fanxuankai.canal.mq.core.listener.ConsumerHelper;
import com.fanxuankai.canal.rocketmq.CanalRocketMqWorker;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * @author fanxuankai
 */
public class CanalRocketMqAutoConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(CanalRocketMqAutoConfiguration.class);

    @Bean
    @ConditionalOnProperty(prefix = CanalMqProperties.PREFIX, name = "enabled", havingValue = "true")
    @ConditionalOnMissingBean
    public CanalRocketMqWorker canalRocketMqWorker(CanalMqProperties canalMqProperties,
                                                   RocketMQTemplate rocketMqTemplate) {
        return CanalRocketMqWorker.newCanalWorker(canalMqProperties.getConfiguration(), canalMqProperties,
                rocketMqTemplate);
    }

    @Bean(initMethod = "start")
    public DefaultMQPushConsumer pushConsumer(RocketMQProperties properties, ConsumerHelper consumerHelper) {
        DefaultMQPushConsumer consumer =
                new DefaultMQPushConsumer(properties.getProducer().getGroup());
        consumer.setNamesrvAddr(properties.getNameServer());
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setMessageModel(MessageModel.BROADCASTING);
        consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            String topic = consumeConcurrentlyContext.getMessageQueue().getTopic();
            list.forEach(messageExt -> consumerHelper.consume(topic, new String(messageExt.getBody())));
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumerHelper.accept((canalListenerDefinition, s) -> {
            try {
                consumer.subscribe(s, "*");
            } catch (MQClientException e) {
                LOGGER.error("订阅失败", e);
            }
        });
        return consumer;
    }
}
