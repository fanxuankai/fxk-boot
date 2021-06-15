package com.fanxuankai.boot.log;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.useragent.Browser;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fanxuankai.boot.log.annotation.Log;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fanxuankai
 */
public class LogMethodInterceptor implements MethodInterceptor {
    private final LogStore logStore;
    private final UsernameService usernameService;

    public LogMethodInterceptor(LogStore logStore, UsernameService usernameService) {
        if (logStore == null) {
            logStore = new LoggerLogStore();
        }
        this.logStore = logStore;
        this.usernameService = usernameService;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        Log logAnnotation = method.getAnnotation(Log.class);
        return proceed(methodInvocation, logAnnotation);
    }

    private Object proceed(MethodInvocation methodInvocation, Log logAnnotation) throws Throwable {
        com.fanxuankai.boot.log.domain.Log log = new com.fanxuankai.boot.log.domain.Log();
        log.setDescription(logAnnotation.value());
        if (usernameService != null) {
            log.setUsername(usernameService.getUsername());
        }
        String ip = NetUtil.getLocalhostStr();
        log.setRequestIp(ip);
        log.setAddress(address(ip));
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
        log.setBrowser(browser());
        log.setCreateTime(new Date());
        Object proceed;
        long start = 0;
        try {
            start = System.currentTimeMillis();
            proceed = methodInvocation.proceed();
            log.setLogType("INFO");
        } catch (Throwable throwable) {
            log.setLogType("ERROR");
            log.setExceptionDetail(ExceptionUtil.stacktraceToString(throwable));
            throw throwable;
        } finally {
            log.setTime(System.currentTimeMillis() - start);
            logStore.store(log);
        }
        return proceed;
    }

    private String address(String ip) {
        String api = String.format("http://whois.pconline.com.cn/ipJson.jsp?ip=%s&json=true", ip);
        JSONObject object = JSONUtil.parseObj(HttpUtil.get(api));
        return object.get("addr", String.class);
    }

    private String browser() {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        Browser browser = userAgent.getBrowser();
        return browser.toString();
    }
}