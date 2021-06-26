package com.fanxuankai.boot.log.interceptor;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.json.JSONUtil;
import com.fanxuankai.boot.log.ClientInfoService;
import com.fanxuankai.boot.log.LogDetailService;
import com.fanxuankai.boot.log.LogInfo;
import com.fanxuankai.boot.log.annotation.Log;
import com.fanxuankai.boot.log.enums.SafetyLevel;
import com.fanxuankai.boot.log.store.LogStore;
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
        // 表达式形式注解为空
        Log logAnnotation = methodInvocation.getMethod().getAnnotation(Log.class);
        LogInfo logInfo = createLog(logAnnotation, methodInvocation);
        Object proceed;
        boolean operationException = false;
        long start = 0;
        try {
            start = System.currentTimeMillis();
            proceed = methodInvocation.proceed();
            logInfo.setTotalTimeMillis(System.currentTimeMillis() - start);
            setupReturnValue(logAnnotation, logInfo, proceed);
        } catch (Throwable throwable) {
            logInfo.setTotalTimeMillis(System.currentTimeMillis() - start);
            operationException = true;
            logInfo.setExceptionDetail(ExceptionUtil.stacktraceToString(throwable));
            throw throwable;
        } finally {
            logInfo.setOperationException(operationException);
            logStore.store(logInfo);
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
    private LogInfo createLog(Log logAnnotation, MethodInvocation methodInvocation) {
        LogInfo logInfo = new LogInfo();
        logInfo.setUsername(logDetailService.getUsername());
        logInfo.setClassName(methodInvocation.getMethod().getDeclaringClass().getName());
        logInfo.setMethodName(methodInvocation.getMethod().getName());
        logInfo.setServerIp(NetUtil.getLocalhostStr());
        setupClientInfo(logInfo);
        logInfo.setCreateTime(new Date());
        if (logAnnotation != null) {
            logInfo.setResource(logAnnotation.value());
            logInfo.setSafetyLevel(logAnnotation.safetyLevel().ordinal());
            if (logAnnotation.params()) {
                setupParams(logInfo, methodInvocation);
            }
        } else {
            logInfo.setSafetyLevel(SafetyLevel.NORMAL.ordinal());
            setupParams(logInfo, methodInvocation);
        }
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
        logInfo.setUrl(clientInfoService.getUrl());
        logInfo.setClientIp(clientInfoService.getIp());
        logInfo.setClientAddress(logDetailService.getAddress(logInfo.getClientIp()));
        logInfo.setBrowser(clientInfoService.getBrowser());
    }

    /**
     * 参数赋值
     *
     * @param logInfo          /
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

    /**
     * 返回值赋值
     *
     * @param logAnnotation /
     * @param logInfo       /
     * @param proceed       /
     */
    private void setupReturnValue(Log logAnnotation, LogInfo logInfo, Object proceed) {
        if (logAnnotation != null && !logAnnotation.returnValue()) {
            return;
        }
        String returnValue;
        try {
            returnValue = JSONUtil.toJsonStr(proceed);
        } catch (Exception e) {
            returnValue = proceed.toString();
        }
        logInfo.setReturnValue(returnValue);
    }
}