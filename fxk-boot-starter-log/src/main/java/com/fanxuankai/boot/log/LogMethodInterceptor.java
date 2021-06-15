package com.fanxuankai.boot.log;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.json.JSONUtil;
import com.fanxuankai.boot.log.annotation.Log;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.DefaultParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fanxuankai
 */
public class LogMethodInterceptor implements MethodInterceptor {
    private final LogStore logStore;
    private final LogDetailService logDetailService;
    private final ClientInfoSupplier clientInfoSupplier;

    public LogMethodInterceptor(LogStore logStore, LogDetailService logDetailService,
                                ClientInfoSupplier clientInfoSupplier) {
        this.logStore = logStore;
        this.logDetailService = logDetailService;
        this.clientInfoSupplier = clientInfoSupplier;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        Log logAnnotation = method.getAnnotation(Log.class);
        return proceed(methodInvocation, logAnnotation);
    }

    private Object proceed(MethodInvocation methodInvocation, Log logAnnotation) throws Throwable {
        com.fanxuankai.boot.log.domain.Log log = new com.fanxuankai.boot.log.domain.Log();
        log.setUsername(logDetailService.getUsername());
        log.setResource(logAnnotation.value());
        log.setSafetyLevel(logAnnotation.safetyLevel().ordinal());
        log.setServerIp(NetUtil.getLocalhostStr());
        if (clientInfoSupplier != null) {
            log.setUrl(clientInfoSupplier.getUrl());
            log.setClientIp(clientInfoSupplier.getIp());
            log.setClientAddress(logDetailService.getAddress(log.getClientIp()));
            log.setBrowser(clientInfoSupplier.getBrowser());
        }
        Method method = methodInvocation.getMethod();
        log.setClassName(method.getDeclaringClass().getName());
        log.setMethodName(method.getName());
        Map<String, Object> params = new HashMap<>(16);
        Object[] arguments = methodInvocation.getArguments();
        DefaultParameterNameDiscoverer discover = new DefaultParameterNameDiscoverer();
        String[] parameters = discover.getParameterNames(method);
        if (parameters != null && arguments != null && arguments.length > 0) {
            params = new HashMap<>(arguments.length);
            for (int i = 0; i < parameters.length; i++) {
                params.put(parameters[i], arguments[i]);
            }
        }
        log.setParams(JSONUtil.toJsonStr(params));
        log.setCreateTime(new Date());
        Object proceed;
        long start = 0;
        try {
            start = System.currentTimeMillis();
            proceed = methodInvocation.proceed();
            log.setOperationException(false);
        } catch (Throwable throwable) {
            log.setOperationException(true);
            log.setExceptionDetail(ExceptionUtil.stacktraceToString(throwable));
            throw throwable;
        } finally {
            log.setTime(System.currentTimeMillis() - start);
            logStore.store(log);
        }
        return proceed;
    }
}