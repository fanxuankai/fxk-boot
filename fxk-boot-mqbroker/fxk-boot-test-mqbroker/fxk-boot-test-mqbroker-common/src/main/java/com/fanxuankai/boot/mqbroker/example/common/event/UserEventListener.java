package com.fanxuankai.boot.mqbroker.example.common.event;

import cn.hutool.json.JSONUtil;
import com.fanxuankai.boot.mqbroker.consume.EventListener;
import com.fanxuankai.boot.mqbroker.consume.Listener;
import com.fanxuankai.boot.mqbroker.example.common.domain.User;
import com.fanxuankai.boot.mqbroker.model.Event;
import org.springframework.stereotype.Service;

/**
 * @author fanxuankai
 */
@Service
@Listener(event = "user", name = "mqBrokerConsumer", waitRateSeconds = 1, waitMaxSeconds = 10)
public class UserEventListener implements EventListener<User> {
    @Override
    public void onEvent(Event<User> event) {
        System.out.println("接收到事件: " + JSONUtil.toJsonStr(event));
    }
}