package com.fanxuankai.boot.mqbroker.consume;

import java.util.function.Consumer;

/**
 * 消息消费者
 *
 * @author fanxuankai
 */
public interface MqConsumer<T> extends Consumer<T> {

}
