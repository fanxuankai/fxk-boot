package com.fanxuankai.boot.mqbroker.consume;

import com.fanxuankai.boot.mqbroker.model.Event;

/**
 * 事件监听者
 *
 * @author fanxuankai
 */
@FunctionalInterface
public interface EventListener<T> {
    /**
     * 监听
     *
     * @param event 事件
     */
    void onEvent(Event<T> event);
}
