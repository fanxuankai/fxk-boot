package com.fanxuankai.boot.oauth2.core.enums;

/**
 * Token 存储类型
 *
 * @author fanxuankai
 */
public enum TokenStoreType {
    /**
     * Json Web Token
     */
    JWT,
    /**
     * Jdbc
     */
    JDBC,
    /**
     * Redis
     */
    REDIS
}
