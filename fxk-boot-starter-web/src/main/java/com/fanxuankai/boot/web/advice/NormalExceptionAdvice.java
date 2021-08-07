package com.fanxuankai.boot.web.advice;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.fanxuankai.commons.domain.Result;
import com.fanxuankai.commons.exception.BizException;
import com.fanxuankai.commons.exception.LockException;
import com.fanxuankai.commons.util.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 常用异常处理，多个 advice 的情况下，该 advice 优先级最低
 *
 * @author fanxuankai
 */
@Order
@RestControllerAdvice
public class NormalExceptionAdvice extends BaseExceptionAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(NormalExceptionAdvice.class);

    /**
     * 业务异常
     *
     * @param e 异常
     * @return 响应体
     */
    @ExceptionHandler({BizException.class})
    public Result<Void> bizExceptionHandler(BizException e) {
        LOGGER.error("业务异常", e);
        return ResultUtils.newResult(e.getStatus());
    }

    /**
     * 分布式锁获取失败
     *
     * @return Result
     */
    @ExceptionHandler({LockException.class})
    public Result<Void> lockExceptionHandler(LockException e) {
        LOGGER.error("分布式锁获取失败", e);
        return ResultUtils.fail(HttpStatus.LOCKED.value(), "当前资源被锁定，请稍后重试");
    }

    /**
     * 其它异常
     *
     * @param e 异常
     * @return 响应体
     */
    @ExceptionHandler({Exception.class})
    @SuppressWarnings("unchecked")
    @Override
    public Result<Void> exceptionHandler(Exception e) {
        Throwable causedBy = ExceptionUtil.getCausedBy(e, BizException.class);
        if (causedBy != null) {
            return bizExceptionHandler((BizException) causedBy);
        }
        return super.exceptionHandler(e);
    }
}
