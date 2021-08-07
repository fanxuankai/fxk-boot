package com.fanxuankai.boot.web.advice;

import com.fanxuankai.commons.domain.Result;
import com.fanxuankai.commons.util.ResultUtils;
import org.redisson.client.RedisException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Redisson 异常处理
 *
 * @author fanxuankai
 */
@Order(1000)
@RestControllerAdvice
@ConditionalOnClass({RedisException.class})
public class RedissonExceptionAdvice extends NormalExceptionAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonExceptionAdvice.class);

    /**
     * Redis 异常
     *
     * @param e 异常
     * @return 响应体
     */
    @ExceptionHandler({RedisException.class})
    public Result<Void> redisExceptionHandler(RedisException e) {
        LOGGER.error("Redis 异常", e);
        return ResultUtils.fail("Redis 网络异常");
    }
}
