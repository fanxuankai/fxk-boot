package com.fanxuankai.boot.log.autoconfigure;

import com.fanxuankai.boot.log.enums.LogStore;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author fanxuankai
 */
@ConfigurationProperties(prefix = "log")
public class LogProperties {
    private LogStore logStore;

    public LogStore getLogStore() {
        return logStore;
    }

    public void setLogStore(LogStore logStore) {
        this.logStore = logStore;
    }
}