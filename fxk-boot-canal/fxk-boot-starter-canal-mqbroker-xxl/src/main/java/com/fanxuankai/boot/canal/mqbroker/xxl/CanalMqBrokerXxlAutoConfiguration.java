package com.fanxuankai.boot.canal.mqbroker.xxl;

import com.fanxuankai.boot.mqbroker.consume.EventListener;
import com.fanxuankai.boot.mqbroker.consume.EventListenerRegistry;
import com.fanxuankai.boot.mqbroker.model.Event;
import com.fanxuankai.boot.mqbroker.model.ListenerMetadata;
import com.fanxuankai.boot.mqbroker.xxl.autoconfigure.MqConsumerHelper;
import com.fanxuankai.canal.mq.core.listener.ConsumerHelper;
import com.xxl.mq.client.consumer.IMqConsumer;
import com.xxl.mq.client.consumer.MqConsumerRegistry;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

import javax.annotation.Resource;

/**
 * @author fanxuankai
 */
public class CanalMqBrokerXxlAutoConfiguration implements ApplicationContextAware {

    @Resource
    private ConsumerHelper consumerHelper;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        consumerHelper.accept((definition, s) -> {
            // EventListenerRegistry 代码注册方式不支持匿名类(无法获取泛型)
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
