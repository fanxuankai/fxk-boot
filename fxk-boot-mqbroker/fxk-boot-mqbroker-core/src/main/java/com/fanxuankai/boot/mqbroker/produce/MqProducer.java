package com.fanxuankai.boot.mqbroker.produce;

/**
 * 消息生产者
 *
 * @author fanxuankai
 */
public interface MqProducer<T> {
    /**
     * 生产
     *
     * @param t 消息
     */
    void produce(T t);

    /**
     * 消息发布确认
     *
     * @return 生成消息需要
     */
    boolean isPublisherCallback();
}
