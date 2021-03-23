package com.fanxuankai.boot.mqbroker.consume;

import com.fanxuankai.boot.mqbroker.exception.EventHandleException;
import com.fanxuankai.boot.mqbroker.model.Event;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author fanxuankai
 */
@Component
public class ManyEventDistributor extends AbstractEventDistributor {

    @Override
    @SuppressWarnings("rawtypes unchecked")
    protected void onEvent(Event<?> event, List<EventListener<?>> eventListeners) {
        Throwable throwable = null;
        boolean success = true;
        for (EventListener eventListener : eventListeners) {
            try {
                eventListener.onEvent(event);
            } catch (Exception e) {
                throwable = e;
                success = false;
                break;
            }
        }
        if (!success) {
            throw new EventHandleException(throwable);
        }
    }

    @Override
    public EventStrategy getEventListenerStrategy() {
        return EventStrategy.MANY;
    }
}
