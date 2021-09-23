package com.fanxuankai.boot.mqbroker.rocket.example;

import cn.hutool.core.thread.ThreadUtil;
import com.fanxuankai.boot.mqbroker.example.common.UserManager;
import com.fanxuankai.boot.mqbroker.example.common.domain.User;
import com.fanxuankai.boot.mqbroker.produce.EventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class EventPublisherTest {

    @Resource
    private EventPublisher<User> eventPublisher;

    @Test
    public void publish() {
        eventPublisher.publish(UserManager.mockData());
        ThreadUtil.sleep(30, TimeUnit.SECONDS);
    }
}
