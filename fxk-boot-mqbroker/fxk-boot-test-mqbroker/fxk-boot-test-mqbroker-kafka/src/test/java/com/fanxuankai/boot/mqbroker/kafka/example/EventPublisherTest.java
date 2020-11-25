package com.fanxuankai.boot.mqbroker.kafka.example;

import com.fanxuankai.boot.mqbroker.example.common.UserManager;
import com.fanxuankai.boot.mqbroker.example.common.domain.User;
import com.fanxuankai.boot.mqbroker.produce.EventPublisher;
import com.fanxuankai.commons.util.concurrent.Threads;
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
        Threads.sleep(30, TimeUnit.SECONDS);
    }
}
