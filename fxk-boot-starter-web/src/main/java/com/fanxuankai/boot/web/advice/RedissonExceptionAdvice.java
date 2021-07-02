package com.fanxuankai.boot.web.advice;

import com.fanxuankai.commons.domain.Result;
import com.fanxuankai.commons.util.ResultUtils;
import org.redisson.client.RedisException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Redisson 异常处理
 *
 * @author fanxuankai
 */
public class RedissonExceptionAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(NormalExceptionAdvice.class);

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
