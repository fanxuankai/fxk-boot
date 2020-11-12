package com.fanxuankai.boot.distributed.lock.annotation;

import com.fanxuankai.boot.distributed.lock.CommonConstants;
import com.fanxuankai.boot.distributed.lock.DistributedLocker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分布式锁
 * 时间单位为 ms
 *
 * @author fanxuankai
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Lock {

    /**
     * 前缀
     *
     * @return 默认为 lock
     */
    String prefix() default CommonConstants.DEFAULT_LOCK_KEY_PREFIX;

    /**
     * 业务名
     *
     * @return 默认为类名+方法名
     */
    String business() default "";

    /**
     * 锁资源, 减小锁的粒度, 使用 spring 表达式, 比如 #user.id
     *
     * @return spring expression language
     */
    String[] resources() default {};

    /**
     * 等待锁的时间
     *
     * @return ms
     */
    long waitTimeMillis() default DistributedLocker.WAIT_TIME_MILLI;

    /**
     * 占用锁的时间
     *
     * @return ms
     */
    long releaseTimeMillis() default DistributedLocker.RELEASE_TIME_MILLIS;

}
