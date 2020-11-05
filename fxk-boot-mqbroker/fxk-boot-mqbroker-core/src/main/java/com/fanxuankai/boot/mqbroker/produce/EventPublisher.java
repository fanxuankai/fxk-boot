package com.fanxuankai.boot.mqbroker.produce;

import com.fanxuankai.boot.mqbroker.model.Event;

/**
 * 事件发布者
 *
 * @author fanxuankai
 */
public interface EventPublisher<T> extends Publisher<Event<T>> {

}
