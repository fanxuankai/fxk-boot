package com.fanxuankai.boot.mqbroker.consume;

import com.fanxuankai.boot.mqbroker.autoconfigure.MqBrokerProperties;
import com.fanxuankai.boot.mqbroker.domain.MsgReceive;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
@Component
public class EventDistributorFactory {

    @Resource
    private MqBrokerProperties mqBrokerProperties;
    private final Map<EventStrategy, AbstractEventDistributor> consumerMap;

    public EventDistributorFactory(List<AbstractEventDistributor> distributors) {
        consumerMap = distributors.stream()
                .collect(Collectors.toMap(AbstractEventDistributor::getEventListenerStrategy,
                        Function.identity()));
    }

    public AbstractEventDistributor get(MsgReceive msg) {
        return consumerMap.get(Optional.ofNullable(mqBrokerProperties.getEventStrategy().get(msg.getTopic()))
                .orElse(EventStrategy.DEFAULT));
    }
}
