package com.fanxuankai.boot.mqbroker.example.common;

import com.fanxuankai.boot.mqbroker.example.common.constant.CommonConstants;
import com.fanxuankai.boot.mqbroker.example.common.domain.User;
import com.fanxuankai.boot.mqbroker.model.Event;
import com.github.jsonzou.jmockdata.JMockData;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author fanxuankai
 */
public class UserManager {
    public static List<Event<User>> mockData() {
        return mockData(null);
    }

    public static List<Event<User>> mockData(Date effectTime) {
        return IntStream.range(0, 1)
                .mapToObj(value -> {
                    Event<User> event = new Event<>();
                    event.setName(CommonConstants.USER_TOPIC);
                    event.setKey(UUID.randomUUID().toString());
                    event.setData(JMockData.mock(User.class));
                    event.setEffectTime(effectTime);
                    return event;
                })
                .collect(Collectors.toList());
    }
}
