package com.fanxuankai.boot.mqbroker.config;

import com.fanxuankai.boot.mqbroker.consume.EventStrategy;
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

    public static String getPREFIX() {
        return PREFIX;
    }

    public static String getDingTalkPrefix() {
        return DING_TALK_PREFIX;
    }

    public int getMsgSize() {
        return msgSize;
    }

    public void setMsgSize(int msgSize) {
        this.msgSize = msgSize;
    }

    public int getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    public long getIntervalMillis() {
        return intervalMillis;
    }

    public void setIntervalMillis(long intervalMillis) {
        this.intervalMillis = intervalMillis;
    }

    public long getPublisherCallbackTimeout() {
        return publisherCallbackTimeout;
    }

    public void setPublisherCallbackTimeout(long publisherCallbackTimeout) {
        this.publisherCallbackTimeout = publisherCallbackTimeout;
    }

    public long getConsumeTimeout() {
        return consumeTimeout;
    }

    public void setConsumeTimeout(long consumeTimeout) {
        this.consumeTimeout = consumeTimeout;
    }

    public boolean isManualAcknowledge() {
        return manualAcknowledge;
    }

    public void setManualAcknowledge(boolean manualAcknowledge) {
        this.manualAcknowledge = manualAcknowledge;
    }

    public Map<String, EventStrategy> getEventStrategy() {
        return eventStrategy;
    }

    public void setEventStrategy(Map<String, EventStrategy> eventStrategy) {
        this.eventStrategy = eventStrategy;
    }

    public DingTalk getDingTalk() {
        return dingTalk;
    }

    public void setDingTalk(DingTalk dingTalk) {
        this.dingTalk = dingTalk;
    }

    public Boolean getDisabledDingTalkPush() {
        return disabledDingTalkPush;
    }

    public void setDisabledDingTalkPush(Boolean disabledDingTalkPush) {
        this.disabledDingTalkPush = disabledDingTalkPush;
    }

    public Boolean getEnabledDelayedMessage() {
        return enabledDelayedMessage;
    }

    public void setEnabledDelayedMessage(Boolean enabledDelayedMessage) {
        this.enabledDelayedMessage = enabledDelayedMessage;
    }

    /**
     * 钉钉配置参数
     */
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

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getEnv() {
            return env;
        }

        public void setEnv(String env) {
            this.env = env;
        }
    }
}
