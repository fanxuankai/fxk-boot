package com.fanxuankai.boot.mqbroker.consume;

import java.util.List;

/**
 * @author fanxuankai
 */
public class SimpleEventListenerContainer implements EventListenerContainer {
    private List<EventListenerBean> listeners;

    @Override
    public List<EventListenerBean> getListeners() {
        return listeners;
    }

    @Override
    public void setListeners(List<EventListenerBean> listeners) {
        this.listeners = listeners;
    }
}
