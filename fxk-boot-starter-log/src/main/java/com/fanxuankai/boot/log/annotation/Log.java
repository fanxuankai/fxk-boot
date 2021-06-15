package com.fanxuankai.boot.log.annotation;

import com.fanxuankai.boot.log.enums.SafetyLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 *
 * @author fanxuankai
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    /**
     * 资源名称,指记录某个资源的日志
     *
     * @return /
     */
    String value();

    /**
     * 安全等级,指该资源的安全等级
     *
     * @return /
     */
    SafetyLevel safetyLevel() default SafetyLevel.NORMAL;
}