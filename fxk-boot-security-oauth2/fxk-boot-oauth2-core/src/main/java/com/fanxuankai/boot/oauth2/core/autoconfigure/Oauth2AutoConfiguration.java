package com.fanxuankai.boot.oauth2.core.autoconfigure;

import com.fanxuankai.boot.oauth2.core.translator.ResultExceptionTranslator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * @author fanxuankai
 */
@EnableConfigurationProperties({Oauth2Properties.class})
public class Oauth2AutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public WebResponseExceptionTranslator<?> webResponseExceptionTranslator() {
        return new ResultExceptionTranslator();
    }
}
