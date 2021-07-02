package com.fanxuankai.boot.redis.autoconfigure;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.fanxuankai.boot.redis.DefaultTypingRedisTemplate;
import com.fanxuankai.boot.redis.RedisUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * RedisTemplate 自动装配
 *
 * @author fanxuankai
 */
@Import({RedisUtils.class})
public class RedisAutoConfiguration {
    @Bean
    public DefaultTypingRedisTemplate defaultTypingRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        DefaultTypingRedisTemplate redisTemplate = new DefaultTypingRedisTemplate();
        RedisSerializer<Object> json = new GenericFastJsonRedisSerializer();
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(json);
        redisTemplate.setHashValueSerializer(json);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        RedisSerializer<Object> json = new GenericFastJsonRedisSerializer();
        redisTemplate.setValueSerializer(json);
        redisTemplate.setHashValueSerializer(json);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
