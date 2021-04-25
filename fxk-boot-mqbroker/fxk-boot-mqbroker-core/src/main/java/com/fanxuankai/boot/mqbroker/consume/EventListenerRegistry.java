package com.fanxuankai.boot.mqbroker.consume;

import cn.hutool.core.collection.CollectionUtil;
import com.fanxuankai.boot.mqbroker.model.ListenerMetadata;
import com.fanxuankai.commons.extra.spring.util.GenericTypeUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 事件注册
 *
 * @author fanxuankai
 */
@Component
public class EventListenerRegistry {
    private Set<ListenerMetadata> listenerMetadataSet;
    private Map<ListenerMetadata, List<EventListener<?>>> eventListenerGrouped;
    private Map<ListenerMetadata, Class<?>> dataType;

    public EventListenerRegistry(List<EventListenerContainer> containerList,
                                 List<EventListener<?>> eventListenerList) {
        init(containerList, eventListenerList);
    }

    private void init(List<EventListenerContainer> containerList,
                      List<EventListener<?>> eventListenerList) {
        List<EventListenerBean> beanList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(containerList)) {
            containerList.stream()
                    .map(EventListenerContainer::getListeners)
                    .filter(CollectionUtil::isNotEmpty)
                    .forEach(beanList::addAll);
        }
        if (CollectionUtil.isNotEmpty(eventListenerList)) {
            for (EventListener<?> eventListener : eventListenerList) {
                Listener listener = AnnotationUtils.findAnnotation(eventListener.getClass(), Listener.class);
                assert listener != null;
                String group = Optional.of(listener.group())
                        .filter(StringUtils::hasText)
                        .orElse(null);
                ListenerMetadata listenerMetadata = new ListenerMetadata();
                listenerMetadata.setGroup(group);
                listenerMetadata.setTopic(listener.event());
                listenerMetadata.setName(listener.name());
                listenerMetadata.setWaitRateSeconds(listener.waitRateSeconds());
                listenerMetadata.setWaitMaxSeconds(listener.waitMaxSeconds());
                beanList.add(new EventListenerBean(listenerMetadata, eventListener));
            }
        }
        eventListenerGrouped = beanList.stream()
                .collect(Collectors.groupingBy(EventListenerBean::getListenerMetadata))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, o -> o.getValue()
                        .stream()
                        .map(EventListenerBean::getEventListener)
                        .collect(Collectors.toList())));
        listenerMetadataSet = eventListenerGrouped.keySet();
        dataType = new HashMap<>(eventListenerGrouped.size());
        for (Map.Entry<ListenerMetadata, List<EventListener<?>>> entry :
                eventListenerGrouped.entrySet()) {
            for (EventListener<?> eventListener : entry.getValue()) {
                dataType.put(entry.getKey(), GenericTypeUtils.getGenericType(eventListener.getClass(),
                        EventListener.class, 0));
            }
        }
    }

    public Set<ListenerMetadata> getAllListenerMetadata() {
        return listenerMetadataSet;
    }

    public List<EventListener<?>> getListeners(ListenerMetadata listenerMetadata) {
        return eventListenerGrouped.get(listenerMetadata);
    }

    public Class<?> getDataType(ListenerMetadata listenerMetadata) {
        return dataType.get(listenerMetadata);
    }
}
