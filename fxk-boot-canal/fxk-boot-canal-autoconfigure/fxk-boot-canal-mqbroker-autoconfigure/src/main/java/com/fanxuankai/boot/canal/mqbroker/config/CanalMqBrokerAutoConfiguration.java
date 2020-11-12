package com.fanxuankai.boot.canal.mqbroker.config;

import com.fanxuankai.boot.canal.mq.config.CanalMqProperties;
import com.fanxuankai.boot.canal.mqbroker.CanalMqBrokerWorker;
import com.fanxuankai.boot.mqbroker.consume.EventListener;
import com.fanxuankai.boot.mqbroker.consume.EventListenerRegistry;
import com.fanxuankai.boot.mqbroker.model.Event;
import com.fanxuankai.boot.mqbroker.model.ListenerMetadata;
import com.fanxuankai.boot.mqbroker.produce.EventPublisher;
import com.fanxuankai.canal.mq.core.listener.ConsumerHelper;
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
public class CanalMqBrokerAutoConfiguration implements ApplicationContextAware {

    @Resource
    private ConsumerHelper consumerHelper;

    @Bean
    @ConditionalOnProperty(prefix = CanalMqProperties.PREFIX, name = "enabled", havingValue = "true")
    public CanalMqBrokerWorker canalMqBrokerWorker(CanalMqProperties canalMqProperties,
                                                   EventPublisher<String> eventPublisher) {
        return CanalMqBrokerWorker.newCanalWorker(canalMqProperties.getConfiguration(), canalMqProperties,
                eventPublisher);
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        consumerHelper.accept((definition, s) -> {
            // EventListenerRegistry 代码注册方式不支持匿名类
            EventListener<String> eventListener = new EventListener<String>() {
                @Override
                public void onEvent(Event<String> event) {
                    consumerHelper.consume(event.getName(), event.getData());
                }
            };
            ListenerMetadata listenerMetadata = new ListenerMetadata();
            listenerMetadata.setGroup(definition.getGroup());
            listenerMetadata.setTopic(s);
            listenerMetadata.setWaitRateSeconds(definition.getWaitRateSeconds());
            listenerMetadata.setWaitMaxSeconds(definition.getWaitMaxSeconds());
            EventListenerRegistry.addListener(listenerMetadata, eventListener);
        });
    }
}
