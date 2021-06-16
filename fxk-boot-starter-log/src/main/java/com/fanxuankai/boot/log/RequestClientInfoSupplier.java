package com.fanxuankai.boot.log;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentInfo;
import cn.hutool.http.useragent.UserAgentUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fanxuankai
 */
public class RequestClientInfoSupplier implements ClientInfoSupplier {
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
}