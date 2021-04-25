package com.fanxuankai.boot.canal.mqbroker.xxl;

import com.fanxuankai.boot.mqbroker.consume.EventListener;
import com.fanxuankai.boot.mqbroker.consume.EventListenerBean;
import com.fanxuankai.boot.mqbroker.consume.EventListenerContainer;
import com.fanxuankai.boot.mqbroker.consume.SimpleEventListenerContainer;
import com.fanxuankai.boot.mqbroker.model.Event;
import com.fanxuankai.boot.mqbroker.model.ListenerMetadata;
import com.fanxuankai.boot.mqbroker.xxl.MqConsumerHelper;
import com.fanxuankai.canal.mq.core.listener.ConsumerHelper;
import com.xxl.mq.client.consumer.IMqConsumer;
import com.xxl.mq.client.consumer.MqConsumerRegistry;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fanxuankai
 */
public class CanalMqBrokerXxlAutoConfiguration {
    @Bean(name = "canalMqBrokerXxlEventListenerContainer")
    public EventListenerContainer eventListenerContainer(ConsumerHelper consumerHelper) {
        SimpleEventListenerContainer container = new SimpleEventListenerContainer();
        List<EventListenerBean> listeners = new ArrayList<>();
        consumerHelper.accept((definition, s) -> {
            ListenerMetadata listenerMetadata = new ListenerMetadata();
            listenerMetadata.setGroup(definition.getGroup());
            listenerMetadata.setTopic(s);
            listenerMetadata.setWaitRateSeconds(definition.getWaitRateSeconds());
            listenerMetadata.setWaitMaxSeconds(definition.getWaitMaxSeconds());
            // 不能使用 lambda, 否则无法获取泛型
            EventListener<String> eventListener = new EventListener<String>() {
                @Override
                public void onEvent(Event<String> event) {
                    consumerHelper.consume(event.getName(), event.getData());
                }
            };
            listeners.add(new EventListenerBean(listenerMetadata, eventListener));
            try {
                IMqConsumer iMqConsumer = (IMqConsumer) MqConsumerHelper.newClass(listenerMetadata)
                        .getConstructor()
                        .newInstance();
                MqConsumerRegistry.registerMqConsumer(iMqConsumer);
            } catch (Exception e) {
                throw new RuntimeException("IMqConsumer 实例化失败", e);
            }
        });
        container.setListeners(listeners);
        return container;
    }
}
