package com.fanxuankai.boot.mqbroker.config;

/**
 * 事件注册钩子
 *
 * @author fanxuankai
 */
@FunctionalInterface
public interface EventListenerRegistryHook {

    /**
     * 钩子方法
     */
    void execute();

}
