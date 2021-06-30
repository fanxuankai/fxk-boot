package com.fanxuankai.boot.resource.annotation;

import com.fanxuankai.boot.resource.autoconfigure.JdbcTokenServiceConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用 Token Jdbc 存储
 *
 * @author fanxuankai
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({JdbcTokenServiceConfiguration.class})
public @interface EnableJdbcTokenStore {
}