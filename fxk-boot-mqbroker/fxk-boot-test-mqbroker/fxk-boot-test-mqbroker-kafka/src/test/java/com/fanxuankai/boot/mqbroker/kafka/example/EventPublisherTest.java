package com.fanxuankai.boot.mqbroker.kafka.example;

import com.fanxuankai.boot.mqbroker.example.common.UserManager;
import com.fanxuankai.boot.mqbroker.example.common.domain.User;
import com.fanxuankai.boot.mqbroker.produce.EventPublisher;
import com.fanxuankai.commons.util.DateUtils;
import com.github.jsonzou.jmockdata.JMockData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
public class EventPublisherTest {

    @Resource
    private EventPublisher<User> eventPublisher;
    @Resource
    private KafkaTemplate<String, User> kafkaTemplate;

    @Test
    public void publish() {
        eventPublisher.publish(UserManager.mockData());
    }

    @Test
    public void delaySend() {
        eventPublisher.publish(UserManager.mockData(DateUtils.plusSeconds(new Date(), 30)));
    }

    @Test
    public void publish1() {
        kafkaTemplate.send("user1", JMockData.mock(User.class));
    }
}
