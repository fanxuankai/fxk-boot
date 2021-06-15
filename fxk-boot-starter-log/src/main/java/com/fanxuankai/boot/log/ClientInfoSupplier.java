package com.fanxuankai.boot.log;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 客户端信息 Supplier
 *
 * @author fanxuankai
 */
public interface ClientInfoSupplier {
    /**
     * 获取 ip
     *
     * @return /
     */
    String getIp();

    /**
     * 获取 url
     *
     * @return /
     */
    String getUrl();

    /**
     * 获取浏览器名
     *
     * @return /
     */
    String getBrowser();

    /**
     * 当前请求
     *
     * @return /
     */
    default Optional<HttpServletRequest> currentRequest() {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return Optional.empty();
        }
        return Optional.of(requestAttributes.getRequest());
    }
}