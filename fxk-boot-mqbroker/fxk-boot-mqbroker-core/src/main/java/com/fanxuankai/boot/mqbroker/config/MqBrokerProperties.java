package com.fanxuankai.boot.mqbroker.config;

import com.fanxuankai.boot.mqbroker.consume.EventStrategy;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Map;

/**
 * @author fanxuankai
 */
@Configuration
@ConfigurationProperties(prefix = MqBrokerProperties.PREFIX)
@Data
public class MqBrokerProperties {
    public static final String PREFIX = "mq-broker";
    public static final String DING_TALK_PREFIX = PREFIX + ".ding-talk";
    /**
     * 拉取消息的数量, 大于 500 时需要设置 mybatis-plus 分页 limit 为-1
     */
    private int msgSize = 1_000;
    /**
     * 最大重试次数
     */
    private int maxRetry = 3;
    /**
     * 拉取数据的间隔 ms
     */
    private long intervalMillis = 300_000;
    /**
     * 发布回调超时 ms
     */
    private long publisherCallbackTimeout = 300_000;
    /**
     * 消费超时 ms
     */
    private long consumeTimeout = 300_000;
    /**
     * 手动签收
     */
    private boolean manualAcknowledge;
    /**
     * key: 事件名 value: EventStrategy
     */
    private Map<String, EventStrategy> eventStrategy = Collections.emptyMap();

    @NestedConfigurationProperty
    private DingTalk dingTalk = new DingTalk();

    /**
     * 禁用钉钉推送
     */
    private Boolean disabledDingTalkPush = Boolean.FALSE;

    /**
     * 开启延迟消息
     */
    private Boolean enabledDelayedMessage = Boolean.FALSE;

    /**
     * 钉钉配置参数
     */
    @Data
    public static class DingTalk {
        /**
         * 是否激活
         */
        private Boolean enabled;
        /**
         * url
         */
        private String url;
        /**
         * 访问令牌
         */
        private String accessToken;
        /**
         * 密钥
         */
        private String secret;
        /**
         * 环境
         */
        private String env;
    }
}
