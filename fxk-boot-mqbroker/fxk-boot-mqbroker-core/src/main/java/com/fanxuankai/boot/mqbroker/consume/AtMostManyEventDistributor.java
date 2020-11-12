package com.fanxuankai.boot.mqbroker.consume;

import com.fanxuankai.boot.mqbroker.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author fanxuankai
 */
@Component
public class AtMostManyEventDistributor extends AbstractEventDistributor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AtMostManyEventDistributor.class);

    @Override
    @SuppressWarnings("rawtypes unchecked")
    protected void onEvent(Event<?> event, List<EventListener<?>> eventListeners) {
        for (EventListener eventListener : eventListeners) {
            try {
                eventListener.onEvent(event);
            } catch (Exception e) {
                LOGGER.error("事件处理异常, key: " + event.getKey(), e);
            }
        }
    }

    @Override
    public EventStrategy getEventListenerStrategy() {
        return EventStrategy.AT_MOST_MANY;
    }
}
