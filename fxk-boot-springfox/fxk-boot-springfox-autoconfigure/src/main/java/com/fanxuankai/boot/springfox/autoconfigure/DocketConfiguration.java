package com.fanxuankai.boot.springfox.autoconfigure;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

/**
 * @author fanxuankai
 */
public class DocketConfiguration {
    /**
     * 开关
     */
    private Boolean enabled;

    /**
     * 项目信息
     */
    @NestedConfigurationProperty
    private ApiInfoConfiguration apiInfo;

    /**
     * 地址
     */
    private String host;

    /**
     * api 配置
     */
    @NestedConfigurationProperty
    private ApisConfiguration apis;

    /**
     * 自定义 header key
     */
    private List<String> headers;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public ApiInfoConfiguration getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(ApiInfoConfiguration apiInfo) {
        this.apiInfo = apiInfo;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public ApisConfiguration getApis() {
        return apis;
    }

    public void setApis(ApisConfiguration apis) {
        this.apis = apis;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }
}
