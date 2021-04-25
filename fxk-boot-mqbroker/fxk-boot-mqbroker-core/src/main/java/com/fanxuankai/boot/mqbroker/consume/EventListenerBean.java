package com.fanxuankai.boot.mqbroker.consume;

import com.fanxuankai.boot.mqbroker.model.ListenerMetadata;

/**
 * 事件监听者 Bean
 *
 * @author fanxuankai
 */
public class EventListenerBean {

    private final ListenerMetadata listenerMetadata;
    private final EventListener<?> eventListener;

    public EventListenerBean(ListenerMetadata listenerMetadata, EventListener<?> eventListener) {
        this.listenerMetadata = listenerMetadata;
        this.eventListener = eventListener;
    }

    /**
     * 监听者信息
     *
     * @return ListenerMetadata
     */
    public ListenerMetadata getListenerMetadata() {
        return listenerMetadata;
    }

    /**
     * 事件监听者
     *
     * @return EventListener
     */
    public EventListener<?> getEventListener() {
        return eventListener;
    }
}
