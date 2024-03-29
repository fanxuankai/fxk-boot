package com.fanxuankai.boot.mqbroker.xxl;

import com.fanxuankai.boot.mqbroker.consume.EventListenerRegistry;
import com.xxl.mq.client.consumer.IMqConsumer;
import com.xxl.mq.client.factory.XxlMqClientFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xuxueli 2018-11-18 21:18:10
 */
public class MqBrokerXxlMqSpringClientFactory implements ApplicationContextAware, DisposableBean {

    // ---------------------- param  ----------------------

    private String adminAddress;
    private String accessToken;
    private EventListenerRegistry eventListenerRegistry;
    private XxlMqClientFactory xxlMqClientFactory;

    public void setAdminAddress(String adminAddress) {
        this.adminAddress = adminAddress;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setEventListenerRegistry(EventListenerRegistry eventListenerRegistry) {
        this.eventListenerRegistry = eventListenerRegistry;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        // load consumer from spring
        List<IMqConsumer> consumerList = eventListenerRegistry.getAllListenerMetadata()
                .parallelStream()
                .map(s -> {
                    try {
                        return (IMqConsumer) MqConsumerUtil.newClass(s.getGroup(), s.getTopic(),
                                XxlMqConsumer.class)
                                .getConstructor()
                                .newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException("IMqConsumer 实例化失败", e);
                    }
                })
                .collect(Collectors.toList());
        // init
        xxlMqClientFactory = new XxlMqClientFactory();
        xxlMqClientFactory.setAdminAddress(adminAddress);
        xxlMqClientFactory.setAccessToken(accessToken);
        xxlMqClientFactory.setConsumerList(consumerList);
        xxlMqClientFactory.init();
    }

    @Override
    public void destroy() throws Exception {
        xxlMqClientFactory.destroy();
    }

}
