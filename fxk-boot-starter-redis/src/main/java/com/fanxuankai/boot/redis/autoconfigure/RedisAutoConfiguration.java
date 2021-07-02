package com.fanxuankai.boot.redis.autoconfigure;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.fanxuankai.boot.redis.DefaultTypingRedisTemplate;
import com.fanxuankai.boot.redis.RedisUtils;
import com.fanxuankai.boot.redis.serializer.FastJson2JsonRedisSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * RedisTemplate 自动装配, 如果装配成功会自动激活 RedisUtils
 *
 * @author fanxuankai
 */
@Import({RedisUtils.class})
public class RedisAutoConfiguration {
    @Bean
    public DefaultTypingRedisTemplate defaultTypingRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        DefaultTypingRedisTemplate redisTemplate = new DefaultTypingRedisTemplate();
        RedisSerializer<Object> json = new GenericFastJsonRedisSerializer();
        // key 和 hashKey 使用字符串序列化
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        // value 和 hashValue 使用 JSON 序列化
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
        RedisSerializer<Object> json = new FastJson2JsonRedisSerializer();
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(json);
        redisTemplate.setHashValueSerializer(json);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
