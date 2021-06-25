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
 * 方法增强具体实现
 *
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
        LogInfo logInfo = createLog(logAnnotation, methodInvocation);
        Object proceed;
        boolean operationException = false;
        long start = 0;
        try {
            start = System.currentTimeMillis();
            proceed = methodInvocation.proceed();
            setupReturnValue(logAnnotation, logInfo, proceed);
        } catch (Throwable throwable) {
            operationException = true;
            logInfo.setExceptionDetail(ExceptionUtil.stacktraceToString(throwable));
            throw throwable;
        } finally {
            logInfo.setTotalTimeMillis(System.currentTimeMillis() - start);
            logInfo.setOperationException(operationException);
            logStore.store(logInfo);
        }
        return proceed;
    }

    private void setupReturnValue(Log logAnnotation, LogInfo logInfo, Object proceed) {
        if (logAnnotation.returnValue()) {
            String returnValue;
            try {
                returnValue = JSONUtil.toJsonStr(proceed);
            } catch (Exception e) {
                returnValue = proceed.toString();
            }
            logInfo.setReturnValue(returnValue);
        }
    }

    /**
     * 创建 log 对象
     *
     * @param logAnnotation    /
     * @param methodInvocation /
     * @return /
     */
    private LogInfo createLog(Log logAnnotation, MethodInvocation methodInvocation) {
        LogInfo logInfo = new LogInfo();
        logInfo.setUsername(logDetailService.getUsername());
        logInfo.setResource(logAnnotation.value());
        logInfo.setSafetyLevel(logAnnotation.safetyLevel().ordinal());
        logInfo.setClassName(methodInvocation.getMethod().getDeclaringClass().getName());
        logInfo.setMethodName(methodInvocation.getMethod().getName());
        logInfo.setServerIp(NetUtil.getLocalhostStr());
        if (logAnnotation.params()) {
            setupParams(logInfo, methodInvocation);
        }
        setupClientInfo(logInfo);
        logInfo.setCreateTime(new Date());
        return logInfo;
    }

    /**
     * 客户端信息赋值
     *
     * @param logInfo /
     */
    private void setupClientInfo(LogInfo logInfo) {
        if (clientInfoService == null) {
            return;
        }
        logInfo.setUri(clientInfoService.getUrl());
        logInfo.setClientIp(clientInfoService.getIp());
        logInfo.setClientAddress(logDetailService.getAddress(logInfo.getClientIp()));
        logInfo.setBrowser(clientInfoService.getBrowser());
    }

    /**
     * 参数赋值
     *
     * @param logInfo              /
     * @param methodInvocation /
     */
    private void setupParams(LogInfo logInfo, MethodInvocation methodInvocation) {
        Object[] arguments = methodInvocation.getArguments();
        DefaultParameterNameDiscoverer discover = new DefaultParameterNameDiscoverer();
        String[] parameters = discover.getParameterNames(methodInvocation.getMethod());
        if (parameters != null && arguments != null && arguments.length > 0) {
            Map<String, Object> params = new HashMap<>(arguments.length);
            for (int i = 0; i < parameters.length; i++) {
                params.put(parameters[i], arguments[i]);
            }
            logInfo.setParams(JSONUtil.toJsonStr(params));
        }
    }
}