package com.fanxuankai.boot.canal.mysql.autoconfigure;

import com.fanxuankai.canal.core.config.CanalConfiguration;
import com.fanxuankai.canal.core.constants.Constants;
import com.fanxuankai.canal.db.core.config.CanalDbConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author fanxuankai
 */
@ConfigurationProperties(prefix = CanalMySqlProperties.PREFIX)
public class CanalMySqlProperties extends CanalDbConfiguration {
    public static final String PREFIX = "fxk." + Constants.PREFIX + Constants.DOT + "mysql";
    /**
     * 是否开启 canal 服务
     */
    private Boolean enabled = Boolean.TRUE;
    @NestedConfigurationProperty
    private CanalConfiguration configuration = new CanalConfiguration();

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public CanalConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(CanalConfiguration configuration) {
        this.configuration = configuration;
    }
}
