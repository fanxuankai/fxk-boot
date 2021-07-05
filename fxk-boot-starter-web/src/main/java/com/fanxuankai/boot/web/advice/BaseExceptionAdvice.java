package com.fanxuankai.boot.web.advice;

import com.fanxuankai.commons.domain.DefaultStatus;
import com.fanxuankai.commons.domain.Result;
import com.fanxuankai.commons.util.OptionalUtils;
import com.fanxuankai.commons.util.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * 异常处理基类
 *
 * @author fanxuankai
 */
public class BaseExceptionAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseExceptionAdvice.class);

    /**
     * 请求方法异常
     *
     * @param e 异常
     * @return 响应体
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Result<Void> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        LOGGER.error("请求方法异常", e);
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
        LOGGER.error("请求方法异常", e);
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
        LOGGER.error("hibernate valid 异常", e);
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
        LOGGER.error("系统异常", e);
        Optional<Result<Void>> resultOptional = OptionalUtils.ofNullable(e.getMessage())
                .map(message -> ResultUtils.fail(DefaultStatus.FAILED.getCode(), message));
        return resultOptional.orElse(ResultUtils.fail());
    }

    /**
     * 参数类型不匹配
     *
     * @param e 异常
     * @return 响应体
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public Result<Void> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e) {
        LOGGER.error("参数类型不匹配", e);
        return ResultUtils.fail(BAD_REQUEST.value(), "参数类型不匹配: " + e.getPropertyName());
    }

    protected Result<Void> bindResultHandler(BindingResult result) {
        if (result.hasErrors()) {
            return ResultUtils.fail(BAD_REQUEST.value(), result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("、")));
        }
        return ResultUtils.fail(BAD_REQUEST.value(), "参数校验失败, 请检查参数");
    }
}
