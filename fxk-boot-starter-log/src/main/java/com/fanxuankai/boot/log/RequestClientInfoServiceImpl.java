package com.fanxuankai.boot.log;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentInfo;
import cn.hutool.http.useragent.UserAgentUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 客户端信息默认实现
 *
 * @author fanxuankai
 */
public class RequestClientInfoServiceImpl implements ClientInfoService {
    @Override
    public String getIp() {
        return currentRequest()
                .map(ServletUtil::getClientIP)
                .orElse(null);
    }

    @Override
    public String getUrl() {
        return currentRequest()
                .map(HttpServletRequest::getRequestURI)
                .orElse(null);
    }

    @Override
    public String getBrowser() {
        return currentRequest()
                .map(request -> request.getHeader("User-Agent"))
                .map(UserAgentUtil::parse)
                .map(UserAgent::getBrowser)
                .map(UserAgentInfo::toString)
                .orElse(null);
    }

    /**
     * 当前请求
     *
     * @return /
     */
    protected Optional<HttpServletRequest> currentRequest() {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return Optional.empty();
        }
        return Optional.of(requestAttributes.getRequest());
    }
}