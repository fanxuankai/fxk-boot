package com.fanxuankai.boot.mqbroker.produce;

import com.fanxuankai.boot.mqbroker.model.Event;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author fanxuankai
 */
@Component
public class DefaultEventPublisher<T> extends AbstractEventPublisher<T> {
    @Override
    public void publish(Event<T> event) {
        publish(event, false);
    }

    @Override
    public void publish(List<Event<T>> events) {
        publish(events, false);
    }

    @Override
    public void publish(Event<T> event, boolean async) {
        persistence(event, async);
    }

    @Override
    public void publish(List<Event<T>> events, boolean async) {
        persistence(events, async);
    }
}
