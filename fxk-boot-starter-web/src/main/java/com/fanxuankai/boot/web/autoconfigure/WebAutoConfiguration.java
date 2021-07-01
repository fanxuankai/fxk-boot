package com.fanxuankai.boot.web.autoconfigure;

import com.fanxuankai.boot.web.converter.JsonMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * @author fanxuankai
 */
@EnableConfigurationProperties({WebProperties.class})
@Import({ExceptionAdviceAutoConfiguration.class})
public class WebAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(WebProperties webProperties) {
        return new JsonMessageConverter(webProperties);
    }
}
