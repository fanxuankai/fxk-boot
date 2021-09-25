package com.fanxuankai.boot.mqbroker.kafka.example;

import cn.hutool.core.thread.ThreadUtil;
import com.fanxuankai.boot.mqbroker.example.common.UserManager;
import com.fanxuankai.boot.mqbroker.example.common.domain.User;
import com.fanxuankai.boot.mqbroker.produce.EventPublisher;
import com.github.jsonzou.jmockdata.JMockData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EventPublisherTest {

    @Resource
    private EventPublisher<User> eventPublisher;
    @Resource
    private KafkaTemplate<String, User> kafkaTemplate;

    @Test
    public void publish() {
        eventPublisher.publish(UserManager.mockData());
        kafkaTemplate.send("user1", JMockData.mock(User.class));
        ThreadUtil.sleep(30, TimeUnit.SECONDS);
    }
}
