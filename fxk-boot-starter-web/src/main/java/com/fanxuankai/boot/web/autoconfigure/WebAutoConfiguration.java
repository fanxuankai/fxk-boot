package com.fanxuankai.boot.web.autoconfigure;

import com.fanxuankai.boot.web.advice.ExceptionAdvice;
import com.fanxuankai.boot.web.converter.JsonMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author fanxuankai
 */
@Configuration
@EnableConfigurationProperties({WebProperties.class})
public class WebAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(WebProperties webProperties) {
        return new JsonMessageConverter(webProperties);
    }

    /**
     * 全局异常处理
     *
     * @author fanxuankai
     */
    @Configuration
    @ConditionalOnProperty(prefix = WebProperties.PREFIX, name = "enable-exception-advice",
            havingValue = "true", matchIfMissing = true)
    @RestControllerAdvice
    public static class ExceptionAdviceConfiguration extends ExceptionAdvice {
    }
}
