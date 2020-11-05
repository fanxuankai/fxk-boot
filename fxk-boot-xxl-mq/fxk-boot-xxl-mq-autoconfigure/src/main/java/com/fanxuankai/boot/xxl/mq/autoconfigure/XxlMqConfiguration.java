package com.fanxuankai.boot.xxl.mq.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author fanxuankai
 */
@Data
@ConfigurationProperties(prefix = "xxl.mq")
public class XxlMqConfiguration {
    private Admin admin;
    private String accessToken;

    @Data
    public static class Admin {
        private String address;
    }
}
