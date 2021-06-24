package com.fanxuankai.boot.mqbroker.xxl.autoconfigure;

import cn.hutool.json.JSONUtil;
import com.fanxuankai.boot.mqbroker.consume.*;
import com.fanxuankai.boot.mqbroker.model.Event;
import com.fanxuankai.boot.mqbroker.model.ListenerMetadata;
import com.fanxuankai.boot.mqbroker.produce.AbstractMqProducer;
import com.fanxuankai.boot.mqbroker.produce.MqProducer;
import com.fanxuankai.boot.mqbroker.xxl.MqConsumerHelper;
import com.xxl.mq.client.consumer.IMqConsumer;
import com.xxl.mq.client.consumer.MqConsumerRegistry;
import com.xxl.mq.client.message.XxlMqMessage;
import com.xxl.mq.client.producer.XxlMqProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author fanxuankai
 */
@Configuration
public class MqBrokerXxlAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(MqProducer.class)
    public AbstractMqProducer mqProducer() {
        return new AbstractMqProducer() {
            @Override
            public void accept(Event<String> event) {
                XxlMqMessage mqMessage = new XxlMqMessage();
                mqMessage.setTopic(event.getName());
                mqMessage.setGroup(event.getGroup());
                Optional.ofNullable(event.getRetryCount())
                        .ifPresent(mqMessage::setRetryCount);
                mqMessage.setEffectTime(event.getEffectTime());
                mqMessage.setData(JSONUtil.toJsonStr(event));
                XxlMqProducer.produce(mqMessage);
            }

            @Override
            public boolean isPublisherCallback() {
                return false;
            }
        };
    }

    @Bean(name = "mqBrokerXxlEventListenerContainer")
    public EventListenerContainer eventListenerContainer(List<EventListener<?>> eventListeners) {
        SimpleEventListenerContainer container = new SimpleEventListenerContainer();
        List<EventListenerBean> listeners = new ArrayList<>();
        for (EventListener<?> eventListener : eventListeners) {
            Listener listener = AnnotationUtils.findAnnotation(eventListener.getClass(), Listener.class);
            assert listener != null;
            String group = Optional.of(listener.group())
                    .filter(StringUtils::hasText)
                    .orElse(null);
            ListenerMetadata listenerMetadata = new ListenerMetadata();
            listenerMetadata.setGroup(group);
            listenerMetadata.setTopic(listener.event());
            listenerMetadata.setName(listener.name());
            listenerMetadata.setWaitRateSeconds(listener.waitRateSeconds());
            listenerMetadata.setWaitMaxSeconds(listener.waitMaxSeconds());
            listeners.add(new EventListenerBean(listenerMetadata, eventListener));
            try {
                IMqConsumer iMqConsumer = (IMqConsumer) MqConsumerHelper.newClass(listenerMetadata)
                        .getConstructor()
                        .newInstance();
                MqConsumerRegistry.registerMqConsumer(iMqConsumer);
            } catch (Exception e) {
                throw new RuntimeException("IMqConsumer 实例化失败", e);
            }
        }
        container.setListeners(listeners);
        return container;
    }
}
