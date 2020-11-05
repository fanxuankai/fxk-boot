package com.fanxuankai.boot.mqbroker.consume;

import com.fanxuankai.boot.mqbroker.exception.EventHandleException;
import com.fanxuankai.boot.mqbroker.model.Event;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author fanxuankai
 */
@Component
public class DefaultEventDistributor extends AbstractEventDistributor {

    @Override
    @SuppressWarnings("rawtypes unchecked")
    protected void onEvent(Event<?> event, List<EventListener<?>> eventListeners) {
        Throwable throwable = null;
        for (EventListener eventListener : eventListeners) {
            try {
                eventListener.onEvent(event);
                return;
            } catch (Exception e) {
                throwable = e;
            }
        }
        throw new EventHandleException(throwable);
    }

    @Override
    public EventStrategy getEventListenerStrategy() {
        return EventStrategy.DEFAULT;
    }
}
