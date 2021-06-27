package com.fanxuankai.boot.web.advice;

import com.fanxuankai.commons.domain.DefaultStatus;
import com.fanxuankai.commons.domain.Result;
import com.fanxuankai.commons.exception.BizException;
import com.fanxuankai.commons.exception.LockException;
import com.fanxuankai.commons.util.OptionalUtils;
import com.fanxuankai.commons.util.ResultUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 *
 * @author fanxuankai
 */
public class ExceptionAdvice {
    /**
     * 业务异常
     *
     * @param e 异常
     * @return 响应体
     */
    @ExceptionHandler({BizException.class})
    public Result<Void> bizExceptionHandler(BizException e) {
        return ResultUtils.newResult(e.getStatus());
    }

    /**
     * 分布式锁异常
     *
     * @return Result
     */
    @ExceptionHandler({LockException.class})
    public Result<Void> lockExceptionHandler() {
        return ResultUtils.fail(HttpStatus.LOCKED.value(), "当前资源被锁定，请稍后重试");
    }

    /**
     * 请求方法异常
     *
     * @param e 异常
     * @return 响应体
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Result<Void> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        return ResultUtils.fail(HttpStatus.METHOD_NOT_ALLOWED.value(), "不支持的方法：" + e.getMethod());
    }

    /**
     * hibernate valid 异常
     *
     * @param e 异常
     * @return 响应体
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Result<Void> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return bindResultHandler(e.getBindingResult());
    }

    /**
     * hibernate valid 异常
     *
     * @param e 异常
     * @return 响应体
     */
    @ExceptionHandler({BindException.class})
    public Result<Void> bindExceptionHandler(BindException e) {
        return bindResultHandler(e);
    }

    /**
     * 其它异常
     *
     * @param e 异常
     * @return 响应体
     */
    @ExceptionHandler({Exception.class})
    public Result<Void> exceptionHandler(Exception e) {
        Optional<Result<Void>> resultOptional = OptionalUtils.ofNullable(e.getMessage())
                .map(message -> ResultUtils.fail(DefaultStatus.FAILED.getCode(), message));
        return resultOptional.orElse(ResultUtils.fail());
    }

    /**
     * 其它异常
     *
     * @param e 异常
     * @return 响应体
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public Result<Void> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e) {
        return ResultUtils.fail("参数类型不匹配");
    }

    private Result<Void> bindResultHandler(BindingResult result) {
        if (result.hasErrors()) {
            return ResultUtils.fail(HttpStatus.BAD_REQUEST.value(), result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("、")));
        }
        return ResultUtils.fail(HttpStatus.BAD_REQUEST.value(), "参数校验失败, 请检查参数");
    }
}
