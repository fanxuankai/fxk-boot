package com.fanxuankai.boot.log.autoconfigure;

import com.fanxuankai.boot.log.ClientInfoService;
import com.fanxuankai.boot.log.RequestClientInfoServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * @author fanxuankai
 */
@Configuration
@ConditionalOnClass({RequestContextHolder.class})
public class ClientInfoServiceConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ClientInfoService clientInfoService() {
        return new RequestClientInfoServiceImpl();
    }
}
