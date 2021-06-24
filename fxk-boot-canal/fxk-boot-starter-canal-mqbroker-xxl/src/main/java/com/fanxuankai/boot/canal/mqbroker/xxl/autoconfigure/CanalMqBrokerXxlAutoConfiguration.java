package com.fanxuankai.boot.canal.mqbroker.xxl.autoconfigure;

import com.fanxuankai.boot.mqbroker.model.ListenerMetadata;
import com.fanxuankai.boot.mqbroker.xxl.MqConsumerHelper;
import com.fanxuankai.canal.mq.core.listener.ConsumerHelper;
import com.xxl.mq.client.consumer.IMqConsumer;
import com.xxl.mq.client.consumer.MqConsumerRegistry;
import org.springframework.context.annotation.Configuration;

/**
 * @author fanxuankai
 */
@Configuration
public class CanalMqBrokerXxlAutoConfiguration {
    public CanalMqBrokerXxlAutoConfiguration(ConsumerHelper consumerHelper) {
        consumerHelper.accept((definition, s) -> {
            ListenerMetadata listenerMetadata = new ListenerMetadata();
            listenerMetadata.setGroup(definition.getGroup());
            listenerMetadata.setTopic(s);
            listenerMetadata.setWaitRateSeconds(definition.getWaitRateSeconds());
            listenerMetadata.setWaitMaxSeconds(definition.getWaitMaxSeconds());
            try {
                IMqConsumer iMqConsumer = (IMqConsumer) MqConsumerHelper.newClass(listenerMetadata)
                        .getConstructor()
                        .newInstance();
                MqConsumerRegistry.registerMqConsumer(iMqConsumer);
            } catch (Exception e) {
                throw new RuntimeException("IMqConsumer 实例化失败", e);
            }
        });
    }
}
