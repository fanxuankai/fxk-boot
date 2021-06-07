package com.fanxuankai.boot.resource.server.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author fanxuankai
 */
@ConfigurationProperties(prefix = "fxk.security.oauth2.resource")
public class ResourceServerProperties {
    private String tokenStore;

    public String getTokenStore() {
        return tokenStore;
    }

    public void setTokenStore(String tokenStore) {
        this.tokenStore = tokenStore;
    }
}
