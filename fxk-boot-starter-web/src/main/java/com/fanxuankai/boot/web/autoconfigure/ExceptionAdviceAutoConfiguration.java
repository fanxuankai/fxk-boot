package com.fanxuankai.boot.web.autoconfigure;

import com.fanxuankai.boot.web.advice.NormalExceptionAdvice;
import com.fanxuankai.boot.web.advice.RedissonExceptionAdvice;
import org.redisson.client.RedisException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理配置
 *
 * @author fanxuankai
 */
@Configuration
@ConditionalOnProperty(prefix = WebProperties.PREFIX, name = "enable-exception-advice", havingValue = "true",
        matchIfMissing = true)
public class ExceptionAdviceAutoConfiguration {
    /**
     * @author fanxuankai
     */
    @Configuration
    @RestControllerAdvice
    public static class NormalExceptionAdviceConfiguration extends NormalExceptionAdvice {
    }

    /**
     * @author fanxuankai
     */
    @Configuration
    @RestControllerAdvice
    @ConditionalOnClass({RedisException.class})
    public static class RedissonExceptionAdviceConfiguration extends RedissonExceptionAdvice {
    }
}