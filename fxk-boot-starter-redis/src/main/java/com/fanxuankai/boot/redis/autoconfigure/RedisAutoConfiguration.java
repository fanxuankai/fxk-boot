package com.fanxuankai.boot.redis.autoconfigure;

import com.fanxuankai.boot.redis.DefaultTypingRedisTemplate;
import com.fanxuankai.boot.redis.RedisUtils;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * RedisTemplate 自动装配, 如果装配成功会自动激活 RedisUtils
 *
 * @author fanxuankai
 */
@Configuration
@Import({RedisUtils.class})
public class RedisAutoConfiguration {
    @Bean
    public DefaultTypingRedisTemplate defaultTypingRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        DefaultTypingRedisTemplate redisTemplate = new DefaultTypingRedisTemplate();
        Jackson2JsonRedisSerializer<?> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // JSON 字符串包含类型信息,类信息作为一个属性
        om.activateDefaultTyping(om.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY);
        RedisSerializer<String> string = RedisSerializer.string();
        jsonRedisSerializer.setObjectMapper(om);
        // key 和 hashKey 使用字符串序列化
        redisTemplate.setKeySerializer(string);
        redisTemplate.setHashKeySerializer(string);
        // value 和 hashValue 使用 JSON 序列化
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        Jackson2JsonRedisSerializer<?> json = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.deactivateDefaultTyping();
        json.setObjectMapper(om);
        RedisSerializer<String> string = RedisSerializer.string();
        redisTemplate.setKeySerializer(string);
        redisTemplate.setHashKeySerializer(string);
        redisTemplate.setValueSerializer(json);
        redisTemplate.setHashValueSerializer(json);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
