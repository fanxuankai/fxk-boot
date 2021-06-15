package com.fanxuankai.boot.log;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * @author fanxuankai
 */
public interface LogDetailService {
    /**
     * 获取当前用户
     *
     * @return /
     */
    String getUsername();

    /**
     * 获取 IP 地址
     *
     * @param ip ip
     * @return /
     */
    default String getAddress(String ip) {
        String api = String.format("http://whois.pconline.com.cn/ipJson.jsp?ip=%s&json=true", ip);
        JSONObject object = JSONUtil.parseObj(HttpUtil.get(api));
        return StrUtil.trim(object.get("addr", String.class));
    }
}