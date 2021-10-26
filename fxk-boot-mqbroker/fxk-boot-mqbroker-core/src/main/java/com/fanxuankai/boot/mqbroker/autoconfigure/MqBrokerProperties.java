package com.fanxuankai.boot.mqbroker.autoconfigure;

import com.fanxuankai.boot.mqbroker.consume.EventStrategy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Collections;
import java.util.Map;

/**
 * @author fanxuankai
 */
@ConfigurationProperties(prefix = MqBrokerProperties.PREFIX)
public class MqBrokerProperties {
    public static final String PREFIX = "fxk.mq-broker";
    /**
     * 消费失败时, 最大重试次数
     */
    private int maxRetry = 3;
    /**
     * 发布回调超时 ms
     */
    private long publisherCallbackTimeout = 300_000;
    /**
     * 是否开启消费补偿。
     * 开启：消费超时会进行消费补偿，会存在重复消费的问题，需要消费端做幂等处理；
     * 关闭：消费超时不会进行消费补偿，会存在消息状态一直处于进行中的问题，需要人工参与处理。
     */
    private boolean enabledConsumptionCompensation = true;
    /**
     * 消费超时 ms
     */
    private long consumeTimeout = 300_000;
    /**
     * 手动签收
     */
    private boolean manualAcknowledge;
    /**
     * 事件策略, 可以配置相同事件多个消费者
     * key: 事件名 value: EventStrategy
     */
    private Map<String, EventStrategy> eventStrategy = Collections.emptyMap();
    /**
     * 拉取消息的数量, 大于 500 时需要设置 mybatis-plus 分页 limit 为-1
     */
    private int msgSize = 100;
    /**
     * 补偿时, 拉取数据的间隔 ms
     */
    private long intervalMillis = 300_000;
    /**
     * 钉钉推送参数
     */
    @NestedConfigurationProperty
    private DingTalk dingTalk = new DingTalk();
    /**
     * 开启延迟消息(比如: RabbitMQ delayed)
     */
    private boolean enabledDelayedMessage;
    /**
     * 延迟发送，到达生效时间才发送
     */
    @NestedConfigurationProperty
    private DelayedSend delayedSend = new DelayedSend();

    public int getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    public long getPublisherCallbackTimeout() {
        return publisherCallbackTimeout;
    }

    public void setPublisherCallbackTimeout(long publisherCallbackTimeout) {
        this.publisherCallbackTimeout = publisherCallbackTimeout;
    }

    public boolean isEnabledConsumptionCompensation() {
        return enabledConsumptionCompensation;
    }

    public void setEnabledConsumptionCompensation(boolean enabledConsumptionCompensation) {
        this.enabledConsumptionCompensation = enabledConsumptionCompensation;
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

    public int getMsgSize() {
        return msgSize;
    }

    public void setMsgSize(int msgSize) {
        this.msgSize = msgSize;
    }

    public long getIntervalMillis() {
        return intervalMillis;
    }

    public void setIntervalMillis(long intervalMillis) {
        this.intervalMillis = intervalMillis;
    }

    public DingTalk getDingTalk() {
        return dingTalk;
    }

    public void setDingTalk(DingTalk dingTalk) {
        this.dingTalk = dingTalk;
    }

    public boolean isEnabledDelayedMessage() {
        return enabledDelayedMessage;
    }

    public void setEnabledDelayedMessage(boolean enabledDelayedMessage) {
        this.enabledDelayedMessage = enabledDelayedMessage;
    }

    public DelayedSend getDelayedSend() {
        return delayedSend;
    }

    public void setDelayedSend(DelayedSend delayedSend) {
        this.delayedSend = delayedSend;
    }

    /**
     * 钉钉配置参数
     */
    public static class DingTalk {
        /**
         * 是否激活
         */
        private boolean enabled;
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

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
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

    public static class DelayedSend {
        /**
         * 是否开启
         */
        private boolean enabled;
        /**
         * 拉取数据的间隔 ms
         */
        private long intervalMillis = 5_000;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public long getIntervalMillis() {
            return intervalMillis;
        }

        public void setIntervalMillis(long intervalMillis) {
            this.intervalMillis = intervalMillis;
        }
    }
}
