package com.fanxuankai.boot.mqbroker.xxl.autoconfigure;

import com.alibaba.fastjson.JSON;
import com.fanxuankai.boot.mqbroker.consume.EventListenerRegistry;
import com.fanxuankai.boot.mqbroker.model.Event;
import com.fanxuankai.boot.mqbroker.produce.AbstractMqProducer;
import com.fanxuankai.boot.mqbroker.produce.MqProducer;
import com.fanxuankai.boot.mqbroker.xxl.MqBrokerXxlMqSpringClientFactory;
import com.fanxuankai.boot.xxl.mq.autoconfigure.XxlMqProperties;
import com.xxl.mq.client.message.XxlMqMessage;
import com.xxl.mq.client.producer.XxlMqProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

/**
 * @author fanxuankai
 */
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
                mqMessage.setData(JSON.toJSONString(event));
                XxlMqProducer.produce(mqMessage);
            }

            @Override
            public boolean isPublisherCallback() {
                return false;
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(MqBrokerXxlMqSpringClientFactory.class)
    public MqBrokerXxlMqSpringClientFactory mqBrokerXxlMqSpringClientFactory(XxlMqProperties xxlMqProperties,
                                                                             EventListenerRegistry eventListenerRegistry) {
        MqBrokerXxlMqSpringClientFactory canalXxlMqSpringClientFactory = new MqBrokerXxlMqSpringClientFactory();
        canalXxlMqSpringClientFactory.setAdminAddress(xxlMqProperties.getAdmin().getAddress());
        canalXxlMqSpringClientFactory.setAccessToken(xxlMqProperties.getAccessToken());
        canalXxlMqSpringClientFactory.setEventListenerRegistry(eventListenerRegistry);
        return canalXxlMqSpringClientFactory;
    }
}
