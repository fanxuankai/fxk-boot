package com.fanxuankai.boot.oauth2.core.autoconfigure;

import com.fanxuankai.boot.oauth2.core.enums.TokenStoreType;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author fanxuankai
 */
@ConfigurationProperties(prefix = Oauth2Properties.PREFIX)
public class Oauth2Properties {
    public static final String PREFIX = "fxk.oauth2";
    public static final String TOKEN_STORE_TYPE = PREFIX + ".token-store-type";

    /**
     * Token 存储类型
     */
    private TokenStoreType tokenStoreType = TokenStoreType.JWT;
    /**
     * 资源服务器配置
     */
    private Resource resource;

    public TokenStoreType getTokenStoreType() {
        return tokenStoreType;
    }

    public void setTokenStoreType(TokenStoreType tokenStoreType) {
        this.tokenStoreType = tokenStoreType;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public static class Resource {
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}