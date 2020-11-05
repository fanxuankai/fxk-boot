package com.fanxuankai.boot.mqbroker.consume;

import com.fanxuankai.boot.mqbroker.model.ListenerMetadata;
import com.fanxuankai.spring.util.GenericTypeUtils;

import java.util.*;

/**
 * 事件注册
 *
 * @author fanxuankai
 */
public class EventListenerRegistry {

    private static final Set<ListenerMetadata> LISTENER_METADATA_SET = new HashSet<>();
    private static final Map<ListenerMetadata, List<EventListener<?>>> EVENT_LISTENERS = new HashMap<>();
    private static final Map<ListenerMetadata, Class<?>> DATA_TYPE = new HashMap<>();

    public static Set<ListenerMetadata> getAllListenerMetadata() {
        return LISTENER_METADATA_SET;
    }

    private static void registerEvent(ListenerMetadata listenerMetadata) {
        LISTENER_METADATA_SET.add(listenerMetadata);
    }

    public static <T> void addListener(ListenerMetadata listenerMetadata, EventListener<T> eventListener) {
        registerEvent(listenerMetadata);
        EVENT_LISTENERS.computeIfAbsent(listenerMetadata, s -> new ArrayList<>()).add(eventListener);
        DATA_TYPE.put(listenerMetadata, GenericTypeUtils.getGenericType(eventListener.getClass(), EventListener.class,
                0));
    }

    public static List<EventListener<?>> getListeners(ListenerMetadata listenerMetadata) {
        return EVENT_LISTENERS.get(listenerMetadata);
    }

    public static Class<?> getDataType(ListenerMetadata listenerMetadata) {
        return DATA_TYPE.get(listenerMetadata);
    }

}
