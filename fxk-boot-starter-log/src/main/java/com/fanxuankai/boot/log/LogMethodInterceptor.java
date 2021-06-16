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
    private final ClientInfoService clientInfoService;

    public LogMethodInterceptor(LogStore logStore, LogDetailService logDetailService,
                                ClientInfoService clientInfoService) {
        this.logStore = logStore;
        this.logDetailService = logDetailService;
        this.clientInfoService = clientInfoService;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        Log logAnnotation = method.getAnnotation(Log.class);
        return proceed(methodInvocation, logAnnotation);
    }

    private Object proceed(MethodInvocation methodInvocation, Log logAnnotation) throws Throwable {
        Method method = methodInvocation.getMethod();
        com.fanxuankai.boot.log.domain.Log log = new com.fanxuankai.boot.log.domain.Log();
        log.setUsername(logDetailService.getUsername());
        log.setResource(logAnnotation.value());
        log.setSafetyLevel(logAnnotation.safetyLevel().ordinal());
        log.setClassName(method.getDeclaringClass().getName());
        log.setMethodName(method.getName());
        log.setServerIp(NetUtil.getLocalhostStr());
        if (logAnnotation.params()) {
            setupParams(log, methodInvocation, method);
        }
        setupClientInfo(log);
        log.setCreateTime(new Date());
        Object proceed;
        long start = 0;
        try {
            start = System.currentTimeMillis();
            proceed = methodInvocation.proceed();
            log.setOperationException(false);
            if (logAnnotation.returnValue()) {
                log.setReturnValue(JSONUtil.toJsonStr(proceed));
            }
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

    private void setupClientInfo(com.fanxuankai.boot.log.domain.Log log) {
        if (clientInfoService == null) {
            return;
        }
        log.setUri(clientInfoService.getUrl());
        log.setClientIp(clientInfoService.getIp());
        log.setClientAddress(logDetailService.getAddress(log.getClientIp()));
        log.setBrowser(clientInfoService.getBrowser());
    }

    private void setupParams(com.fanxuankai.boot.log.domain.Log log,
                             MethodInvocation methodInvocation, Method method) {
        Object[] arguments = methodInvocation.getArguments();
        DefaultParameterNameDiscoverer discover = new DefaultParameterNameDiscoverer();
        String[] parameters = discover.getParameterNames(method);
        if (parameters != null && arguments != null && arguments.length > 0) {
            Map<String, Object> params = new HashMap<>(arguments.length);
            for (int i = 0; i < parameters.length; i++) {
                params.put(parameters[i], arguments[i]);
            }
            log.setParams(JSONUtil.toJsonStr(params));
        }
    }
}