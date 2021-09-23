package com.fanxuankai.boot.redis;

import com.fanxuankai.boot.redis.domain.User;
import com.github.jsonzou.jmockdata.JMockData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author fanxuankai
 */
@SpringBootTest
public class RedisTest {
    @Test
    public void testUtils() {
        User user = JMockData.mock(User.class);
        RedisUtils.ValueOps.set("user", user);
        user = RedisUtils.ValueOps.get("user");
        System.out.println(user);
    }

    @Resource
    private RedisTemplate<String, User> redisTemplate;

    @Test
    public void testTemplate() {
        User user = JMockData.mock(User.class);
        redisTemplate.opsForValue().set("user", user);
        user = redisTemplate.opsForValue().get("user");
        System.out.println(user);
    }
}
