package com.fanxuankai.boot.mqbroker.consume;

import com.fanxuankai.boot.mqbroker.model.Event;
import com.fanxuankai.boot.mqbroker.model.ListenerMetadata;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author fanxuankai
 */
public abstract class AbstractEventDistributor implements EventDistributor {

    private final EventListenerRegistry eventListenerRegistry;

    public AbstractEventDistributor(EventListenerRegistry eventListenerRegistry) {
        this.eventListenerRegistry = eventListenerRegistry;
    }

    @Override
    public void distribute(Event<?> event) {
        ListenerMetadata listenerMetadata = new ListenerMetadata();
        listenerMetadata.setGroup(event.getGroup());
        listenerMetadata.setTopic(event.getName());
        List<EventListener<?>> eventListeners = eventListenerRegistry.getListeners(listenerMetadata);
        if (CollectionUtils.isEmpty(eventListeners)) {
            return;
        }
        onEvent(event, eventListeners);
    }

    /**
     * 消费实现(策略方法)
     *
     * @param event          事件
     * @param eventListeners 事件监听器
     */
    protected abstract void onEvent(Event<?> event, List<EventListener<?>> eventListeners);

    /**
     * 适用的事件监听策略
     *
     * @return 事件监听策略
     */
    public abstract EventStrategy getEventListenerStrategy();

}
