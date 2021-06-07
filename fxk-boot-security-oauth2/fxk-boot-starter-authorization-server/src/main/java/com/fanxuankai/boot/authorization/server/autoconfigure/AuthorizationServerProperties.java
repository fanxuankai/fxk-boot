package com.fanxuankai.boot.authorization.server.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author fanxuankai
 */
@ConfigurationProperties(prefix = "fxk.security.oauth2.authorization")
public class AuthorizationServerProperties {
    private String tokenStore;

    public String getTokenStore() {
        return tokenStore;
    }

    public void setTokenStore(String tokenStore) {
        this.tokenStore = tokenStore;
    }
}
