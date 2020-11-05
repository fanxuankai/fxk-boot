package com.fanxuankai.boot.mqbroker.consume;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author fanxuankai
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Listener {
    /**
     * 分组
     *
     * @return String
     */
    String group() default "";

    /**
     * 事件名
     *
     * @return String
     */
    String event();

    /**
     * 消费者名, 仅 lite-xxl-mq 生效
     *
     * @return String
     */
    String name() default "";

    /**
     * 线程等待递增时长 s
     *
     * @return int
     */
    int waitRateSeconds() default 10;

    /**
     * 线程等待最大时长 s
     *
     * @return int
     */
    int waitMaxSeconds() default 60;
}
