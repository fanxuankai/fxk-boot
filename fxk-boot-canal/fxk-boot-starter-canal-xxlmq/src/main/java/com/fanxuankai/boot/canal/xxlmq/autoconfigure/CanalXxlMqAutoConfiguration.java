package com.fanxuankai.boot.canal.xxlmq.autoconfigure;

import com.fanxuankai.boot.canal.mq.config.autoconfigure.CanalMqProperties;
import com.fanxuankai.canal.mq.core.listener.ConsumerHelper;
import com.fanxuankai.canal.mq.core.model.ListenerMetadata;
import com.fanxuankai.canal.xxlmq.CanalXxlMqWorker;
import com.fanxuankai.canal.xxlmq.MqConsumerHelper;
import com.xxl.mq.client.consumer.IMqConsumer;
import com.xxl.mq.client.consumer.MqConsumerRegistry;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;

import javax.annotation.Resource;

/**
 * @author fanxuankai
 */
public class CanalXxlMqAutoConfiguration implements ApplicationContextAware {

    @Resource
    private ConsumerHelper consumerHelper;

    @Bean
    @ConditionalOnProperty(prefix = CanalMqProperties.PREFIX, name = "enabled", havingValue = "true")
    public CanalXxlMqWorker canalXxlMqWorker(CanalMqProperties canalMqProperties) {
        return CanalXxlMqWorker.newCanalWorker(canalMqProperties.getConfiguration(), canalMqProperties);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        consumerHelper.accept((definition, s) -> {
            try {
                ListenerMetadata listenerMetadata = new ListenerMetadata();
                listenerMetadata.setGroup(definition.getGroup());
                listenerMetadata.setTopic(s);
                listenerMetadata.setWaitRateSeconds(definition.getWaitRateSeconds());
                listenerMetadata.setWaitMaxSeconds(definition.getWaitMaxSeconds());
                IMqConsumer mqConsumer = (IMqConsumer) MqConsumerHelper.newClass(listenerMetadata)
                        .getConstructor(ConsumerHelper.class)
                        .newInstance(consumerHelper);
                MqConsumerRegistry.registerMqConsumer(mqConsumer);
            } catch (Exception e) {
                throw new RuntimeException("IMqConsumer 实例化失败", e);
            }
        });
    }
}
