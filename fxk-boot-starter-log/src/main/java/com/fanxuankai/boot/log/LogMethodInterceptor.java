package com.fanxuankai.boot.log;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.json.JSONUtil;
import com.fanxuankai.boot.log.annotation.Log;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.DefaultParameterNameDiscoverer;

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
        return enhanceProceed(methodInvocation);
    }

    /**
     * 增强处理
     *
     * @param methodInvocation /
     * @return /
     * @throws Throwable /
     */
    private Object enhanceProceed(MethodInvocation methodInvocation) throws Throwable {
        Log logAnnotation = methodInvocation.getMethod().getAnnotation(Log.class);
        com.fanxuankai.boot.log.domain.Log log = createLog(logAnnotation, methodInvocation);
        Object proceed;
        boolean operationException = false;
        long start = 0;
        try {
            start = System.currentTimeMillis();
            proceed = methodInvocation.proceed();
            if (logAnnotation.returnValue()) {
                log.setReturnValue(JSONUtil.toJsonStr(proceed));
            }
        } catch (Throwable throwable) {
            operationException = true;
            log.setExceptionDetail(ExceptionUtil.stacktraceToString(throwable));
            throw throwable;
        } finally {
            log.setTime(System.currentTimeMillis() - start);
            log.setOperationException(operationException);
            logStore.store(log);
        }
        return proceed;
    }

    /**
     * 创建 log 对象
     *
     * @param logAnnotation    /
     * @param methodInvocation /
     * @return /
     */
    private com.fanxuankai.boot.log.domain.Log createLog(Log logAnnotation, MethodInvocation methodInvocation) {
        com.fanxuankai.boot.log.domain.Log log = new com.fanxuankai.boot.log.domain.Log();
        log.setUsername(logDetailService.getUsername());
        log.setResource(logAnnotation.value());
        log.setSafetyLevel(logAnnotation.safetyLevel().ordinal());
        log.setClassName(methodInvocation.getMethod().getDeclaringClass().getName());
        log.setMethodName(methodInvocation.getMethod().getName());
        log.setServerIp(NetUtil.getLocalhostStr());
        if (logAnnotation.params()) {
            setupParams(log, methodInvocation);
        }
        setupClientInfo(log);
        log.setCreateTime(new Date());
        return log;
    }

    /**
     * 客户端信息赋值
     *
     * @param log /
     */
    private void setupClientInfo(com.fanxuankai.boot.log.domain.Log log) {
        if (clientInfoService == null) {
            return;
        }
        log.setUri(clientInfoService.getUrl());
        log.setClientIp(clientInfoService.getIp());
        log.setClientAddress(logDetailService.getAddress(log.getClientIp()));
        log.setBrowser(clientInfoService.getBrowser());
    }

    /**
     * 参数赋值
     *
     * @param log              /
     * @param methodInvocation /
     */
    private void setupParams(com.fanxuankai.boot.log.domain.Log log, MethodInvocation methodInvocation) {
        Object[] arguments = methodInvocation.getArguments();
        DefaultParameterNameDiscoverer discover = new DefaultParameterNameDiscoverer();
        String[] parameters = discover.getParameterNames(methodInvocation.getMethod());
        if (parameters != null && arguments != null && arguments.length > 0) {
            Map<String, Object> params = new HashMap<>(arguments.length);
            for (int i = 0; i < parameters.length; i++) {
                params.put(parameters[i], arguments[i]);
            }
            log.setParams(JSONUtil.toJsonStr(params));
        }
    }
}