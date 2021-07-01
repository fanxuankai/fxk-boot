package com.fanxuankai.boot.xxl.mq.autoconfigure;

import com.xxl.mq.client.factory.impl.XxlMqSpringClientFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author fanxuankai
 */
@EnableConfigurationProperties(XxlMqProperties.class)
@EnableScheduling
public class XxlMqAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public XxlMqSpringClientFactory xxlMqSpringClientFactory(XxlMqProperties xxlMqProperties) {
        XxlMqSpringClientFactory xxlMqSpringClientFactory = new XxlMqSpringClientFactory();
        xxlMqSpringClientFactory.setAdminAddress(xxlMqProperties.getAdmin().getAddress());
        xxlMqSpringClientFactory.setAccessToken(xxlMqProperties.getAccessToken());
        return xxlMqSpringClientFactory;
    }
}
