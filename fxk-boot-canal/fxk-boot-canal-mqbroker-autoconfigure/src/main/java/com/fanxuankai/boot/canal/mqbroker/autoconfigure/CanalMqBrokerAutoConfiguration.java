package com.fanxuankai.boot.canal.mqbroker.autoconfigure;

import com.fanxuankai.boot.canal.mq.config.autoconfigure.CanalMqProperties;
import com.fanxuankai.boot.canal.mqbroker.CanalMqBrokerWorker;
import com.fanxuankai.boot.mqbroker.consume.EventListener;
import com.fanxuankai.boot.mqbroker.consume.EventListenerBean;
import com.fanxuankai.boot.mqbroker.consume.EventListenerContainer;
import com.fanxuankai.boot.mqbroker.consume.SimpleEventListenerContainer;
import com.fanxuankai.boot.mqbroker.model.Event;
import com.fanxuankai.boot.mqbroker.model.ListenerMetadata;
import com.fanxuankai.boot.mqbroker.produce.EventPublisher;
import com.fanxuankai.canal.mq.core.listener.ConsumerHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fanxuankai
 */
public class CanalMqBrokerAutoConfiguration {
    @Bean
    @ConditionalOnProperty(prefix = CanalMqProperties.PREFIX, name = "enabled", havingValue = "true")
    @ConditionalOnMissingBean
    public CanalMqBrokerWorker canalMqBrokerWorker(CanalMqProperties canalMqProperties,
                                                   EventPublisher<String> eventPublisher) {
        return CanalMqBrokerWorker.newCanalWorker(canalMqProperties.getConfiguration(), canalMqProperties,
                eventPublisher);
    }

    @Bean
    public EventListenerContainer eventListenerContainer(ConsumerHelper consumerHelper) {
        SimpleEventListenerContainer container = new SimpleEventListenerContainer();
        List<EventListenerBean> listeners = new ArrayList<>();
        consumerHelper.accept((definition, s) -> {
            // 不能使用 lambda, 否则无法获取泛型
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
            listeners.add(new EventListenerBean(listenerMetadata, eventListener));
        });
        container.setListeners(listeners);
        return container;
    }
}
