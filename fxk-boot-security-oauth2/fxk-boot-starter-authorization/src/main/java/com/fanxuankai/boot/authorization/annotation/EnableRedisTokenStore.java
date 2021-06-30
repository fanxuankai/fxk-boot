package com.fanxuankai.boot.authorization.annotation;

import com.fanxuankai.boot.authorization.autoconfigure.RedisTokenServiceConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用 Token Redis 存储
 *
 * @author fanxuankai
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({RedisTokenServiceConfiguration.class})
public @interface EnableRedisTokenStore {
}
