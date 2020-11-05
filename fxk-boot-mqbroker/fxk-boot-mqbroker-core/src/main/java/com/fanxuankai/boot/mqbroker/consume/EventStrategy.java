package com.fanxuankai.boot.mqbroker.consume;

/**
 * 事件策略
 *
 * @author fanxuankai
 */
public enum EventStrategy {
    /**
     * 一次
     */
    DEFAULT,
    /**
     * 至少一次
     */
    AT_LEAST_ONCE,
    /**
     * 零次或者一次
     */
    AT_MOST_ONCE,
    /**
     * 零次或者多次
     */
    AT_MOST_MANY,
    ;
}
