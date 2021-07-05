package com.fanxuankai.boot.web.autoconfigure;

import com.fanxuankai.boot.web.advice.NormalExceptionAdvice;
import com.fanxuankai.boot.web.advice.RedissonExceptionAdvice;
import org.redisson.client.RedisException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理配置，多个异常处理器需要配置优先级
 *
 * @author fanxuankai
 */
@ConditionalOnProperty(prefix = WebProperties.PREFIX, name = "enable-exception-advice", havingValue = "true",
        matchIfMissing = true)
public class ExceptionAdviceAutoConfiguration {
    /**
     * 最低优先级
     *
     * @author fanxuankai
     */
    @Order
    @Configuration
    @RestControllerAdvice
    public static class NormalExceptionAdviceConfiguration extends NormalExceptionAdvice {
    }

    /**
     * 最高优先级
     *
     * @author fanxuankai
     */
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Configuration
    @RestControllerAdvice
    @ConditionalOnClass({RedisException.class})
    public static class RedissonExceptionAdviceConfiguration extends RedissonExceptionAdvice {
    }
}