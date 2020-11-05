package com.fanxuankai.boot.mqbroker.consume;

import com.fanxuankai.boot.mqbroker.config.MqBrokerProperties;
import com.fanxuankai.boot.mqbroker.domain.MsgReceive;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
@Component
public class EventDistributorFactory implements ApplicationContextAware {

    @Resource
    private MqBrokerProperties mqBrokerProperties;

    private Map<EventStrategy, AbstractEventDistributor> consumerMap = Collections.emptyMap();

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        consumerMap = applicationContext.getBeansOfType(AbstractEventDistributor.class)
                .values()
                .stream()
                .collect(Collectors.toMap(AbstractEventDistributor::getEventListenerStrategy, o -> o));
    }

    public AbstractEventDistributor get(MsgReceive msg) {
        return consumerMap.get(Optional.ofNullable(mqBrokerProperties.getEventStrategy().get(msg.getTopic()))
                .orElse(EventStrategy.DEFAULT));
    }
}
