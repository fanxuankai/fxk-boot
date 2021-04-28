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
    /**
     * 消费失败时, 最大重试次数
     */
    private int maxRetry = 3;
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
     * 事件策略, 可以配置相同事件多个消费者
     * key: 事件名 value: EventStrategy
     */
    private Map<String, EventStrategy> eventStrategy = Collections.emptyMap();
    /**
     * 补偿时, 拉取消息的数量, 大于 500 时需要设置 mybatis-plus 分页 limit 为-1
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
    private DingTalk dingTalk;
    /**
     * 开启延迟消息
     */
    private Boolean enabledDelayedMessage = Boolean.FALSE;
    /**
     * 元数据
     */
    @NestedConfigurationProperty
    private MetaData metaData;

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

    public Boolean getEnabledDelayedMessage() {
        return enabledDelayedMessage;
    }

    public void setEnabledDelayedMessage(Boolean enabledDelayedMessage) {
        this.enabledDelayedMessage = enabledDelayedMessage;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
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

    /**
     * 元数据
     */
    public static class MetaData {
        /**
         * 发送表的表名
         */
        private String msgSendTable;
        /**
         * 接收表的表名
         */
        private String msgReceiveTable;
        /**
         * 创建时间字段名
         */
        private String createDateColumn;
        /**
         * 修改时间字段名
         */
        private String lastModifiedDateColumn;

        public String getMsgSendTable() {
            return msgSendTable;
        }

        public void setMsgSendTable(String msgSendTable) {
            this.msgSendTable = msgSendTable;
        }

        public String getMsgReceiveTable() {
            return msgReceiveTable;
        }

        public void setMsgReceiveTable(String msgReceiveTable) {
            this.msgReceiveTable = msgReceiveTable;
        }

        public String getCreateDateColumn() {
            return createDateColumn;
        }

        public void setCreateDateColumn(String createDateColumn) {
            this.createDateColumn = createDateColumn;
        }

        public String getLastModifiedDateColumn() {
            return lastModifiedDateColumn;
        }

        public void setLastModifiedDateColumn(String lastModifiedDateColumn) {
            this.lastModifiedDateColumn = lastModifiedDateColumn;
        }
    }
}
