package com.fanxuankai.boot.mqbroker.consume;

import java.util.List;

/**
 * @author fanxuankai
 */
public interface EventListenerContainer {
    /**
     * 获取事件监听者
     *
     * @return /
     */
    List<EventListenerBean> getListeners();

    /**
     * 设置事件监听者
     *
     * @param listeners /
     */
    void setListeners(List<EventListenerBean> listeners);
}
