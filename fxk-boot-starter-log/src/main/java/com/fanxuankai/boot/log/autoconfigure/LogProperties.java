package com.fanxuankai.boot.log.autoconfigure;

import com.fanxuankai.boot.log.enums.StoreType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 配置
 *
 * @author fanxuankai
 */
@ConfigurationProperties(prefix = LogProperties.PREFIX)
public class LogProperties {
    public static final String PREFIX = "fxk.log";
    public static final String STORE_TYPE = PREFIX + ".store-type";
    public static final String EXPRESSIONS = PREFIX + ".expressions";

    /**
     * 切入点表达式
     */
    private String expressions;
    /**
     * 存储方式
     */
    private StoreType storeType;
    /**
     * Jdbc 存储配置
     */
    @NestedConfigurationProperty
    private JdbcStore jdbcStore;

    public String getExpressions() {
        return expressions;
    }

    public void setExpressions(String expressions) {
        this.expressions = expressions;
    }

    public StoreType getStoreType() {
        return storeType;
    }

    public void setStoreType(StoreType storeType) {
        this.storeType = storeType;
    }

    public JdbcStore getJdbcStore() {
        return jdbcStore;
    }

    public void setJdbcStore(JdbcStore jdbcStore) {
        this.jdbcStore = jdbcStore;
    }

    public static class JdbcStore {
        /**
         * 表名
         */
        private String tableName;

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }
    }
}