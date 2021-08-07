package com.fanxuankai.boot.log;

/**
 * 客户端信息
 *
 * @author fanxuankai
 */
public interface ClientInfoService {
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
}