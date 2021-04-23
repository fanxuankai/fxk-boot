package com.fanxuankai.boot.xxl.mq.autoconfigure;

import com.xxl.mq.client.factory.impl.XxlMqSpringClientFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author fanxuankai
 */
@Configuration
@EnableConfigurationProperties(XxlMqConfiguration.class)
@EnableScheduling
public class XxlMqAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public XxlMqSpringClientFactory xxlMqSpringClientFactory(XxlMqConfiguration xxlMqConfiguration) {
        XxlMqSpringClientFactory xxlMqSpringClientFactory = new XxlMqSpringClientFactory();
        xxlMqSpringClientFactory.setAdminAddress(xxlMqConfiguration.getAdmin().getAddress());
        xxlMqSpringClientFactory.setAccessToken(xxlMqConfiguration.getAccessToken());
        return xxlMqSpringClientFactory;
    }
}
