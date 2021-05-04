package com.fanxuankai.boot.resource.server.autoconfigure;

import com.fanxuankai.boot.resource.server.adapter.Oauth2ResourceServerConfigurerAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author fanxuankai
 */
@EnableResourceServer
public class Oauth2ResourceServerAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ResourceServerConfigurerAdapter resourceServerConfigurerAdapter() {
        return new Oauth2ResourceServerConfigurerAdapter();
    }
}
