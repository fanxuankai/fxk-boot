package com.fanxuankai.boot.redis.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.IOUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * @author fanxuankai
 */
public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<Object> {

    private final static ParserConfig DEFAULT_REDIS_CONFIG = new ParserConfig();

    static {
        DEFAULT_REDIS_CONFIG.setAutoTypeSupport(true);
    }

    @Override
    public byte[] serialize(Object object) throws SerializationException {
        if (object == null) {
            return new byte[0];
        }
        try {
            return JSON.toJSONBytes(object);
        } catch (Exception ex) {
            throw new SerializationException("Could not serialize: " + ex.getMessage(), ex);
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return JSON.parseObject(new String(bytes, IOUtils.UTF8), Object.class, DEFAULT_REDIS_CONFIG);
        } catch (Exception ex) {
            throw new SerializationException("Could not deserialize: " + ex.getMessage(), ex);
        }
    }
}
