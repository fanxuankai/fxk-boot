package com.fanxuankai.boot.mqbroker.consume;

import com.fanxuankai.boot.mqbroker.model.Event;

/**
 * 事件分发器
 *
 * @author fanxuankai
 */
public interface EventDistributor {
    /**
     * 分发
     *
     * @param event 事件
     */
    void distribute(Event<?> event);
}
