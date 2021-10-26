package com.fanxuankai.boot.mqbroker.kafka.example.handler;

import com.fanxuankai.boot.mqbroker.example.common.domain.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
public class UserListener {
    @KafkaListener(topics = {"user1"})
    public void process(User data) {
        System.out.println("Receiver  : " + data);
    }
}

