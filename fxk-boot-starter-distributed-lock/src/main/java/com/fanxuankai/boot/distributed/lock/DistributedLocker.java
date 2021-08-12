package com.fanxuankai.boot.distributed.lock;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * 分布式锁
 * 时间单位为 ms
 *
 * @author fanxuankai
 */
public interface DistributedLocker {

    long WAIT_TIME_MILLI = 1000;
    long RELEASE_TIME_MILLIS = 5000;

    /**
     * 加分布式锁
     *
     * @param key         the key
     * @param waitTime    等待时间
     * @param releaseTime 释放时间
     * @return 加锁是否成功
     */
    boolean lock(String key, long waitTime, long releaseTime);

    /**
     * 释放分布式锁
     *
     * @param key the lock
     */
    void unlock(String key);

    /**
     * 加分布式锁
     *
     * @param key the key
     * @return 加锁是否成功
     */
    default boolean lock(String key) {
        return lock(key, WAIT_TIME_MILLI, RELEASE_TIME_MILLIS);
    }

    /**
     * 加分布式锁, 自动释放锁
     *
     * @param key         key
     * @param waitTime    等待时间
     * @param releaseTime 释放时间
     * @param callable    加锁成功才会执行
     * @param <T>         the callable item type
     * @return callable 的返回值
     * @throws Exception callable exception
     */
    <T> T lock(String key, long waitTime, long releaseTime, Callable<T> callable) throws Exception;

    /**
     * 加分布式锁, 自动释放锁
     *
     * @param key      key
     * @param callable 加锁成功才会执行
     * @param <T>      the callable item type
     * @return callable 的返回值
     * @throws Exception callable exception
     */
    default <T> T lock(String key, Callable<T> callable) throws Exception {
        return lock(key, WAIT_TIME_MILLI, RELEASE_TIME_MILLIS, callable);
    }

    /**
     * 加分布式锁, 自动释放锁
     *
     * @param key         key
     * @param waitTime    等待时间
     * @param releaseTime 释放时间
     * @param runnable    加锁成功才会执行
     */
    void lock(String key, long waitTime, long releaseTime, Runnable runnable);

    /**
     * 加分布式锁, 自动释放锁
     *
     * @param key      key
     * @param runnable 加锁成功才会执行
     */
    default void lock(String key, Runnable runnable) {
        lock(key, WAIT_TIME_MILLI, RELEASE_TIME_MILLIS, runnable);
    }

    /**
     * 加分布式锁, 自动释放锁
     *
     * @param business  业务名
     * @param resources 锁资源
     * @param runnable  加锁成功才会执行
     */
    default void lock(String business, List<Object> resources, Runnable runnable) {
        lock(null, business, resources, runnable);
    }

    /**
     * 加分布式锁, 自动释放锁
     *
     * @param business    业务名
     * @param waitTime    等待时间
     * @param releaseTime 释放时间
     * @param resources   锁资源
     * @param runnable    加锁成功才会执行
     */
    default void lock(String business, long waitTime, long releaseTime, List<Object> resources, Runnable runnable) {
        lock(null, business, waitTime, releaseTime, resources, runnable);
    }

    /**
     * 加分布式锁, 自动释放锁
     *
     * @param prefix    前缀
     * @param business  业务名
     * @param resources 锁资源
     * @param runnable  加锁成功才会执行
     */
    default void lock(String prefix, String business, List<Object> resources, Runnable runnable) {
        lock(LockKeyMaker.makeKey(prefix, business, resources), runnable);
    }

    /**
     * 加分布式锁, 自动释放锁
     *
     * @param prefix      前缀
     * @param business    业务名
     * @param waitTime    等待时间
     * @param releaseTime 释放时间
     * @param resources   锁资源
     * @param runnable    加锁成功才会执行
     */
    default void lock(String prefix, String business, long waitTime, long releaseTime, List<Object> resources,
                      Runnable runnable) {
        lock(LockKeyMaker.makeKey(prefix, business, resources), waitTime, releaseTime, runnable);
    }

    /**
     * 加分布式锁, 自动释放锁
     *
     * @param business  业务名
     * @param resources 锁资源
     * @param callable  加锁成功才会执行
     * @param <T>       the callable item type
     * @return callable 的返回值
     * @throws Exception callable exception
     */
    default <T> T lock(String business, List<Object> resources, Callable<T> callable) throws Exception {
        return lock(null, business, resources, callable);
    }

    /**
     * 加分布式锁, 自动释放锁
     *
     * @param business    业务名
     * @param waitTime    等待时间
     * @param releaseTime 释放时间
     * @param resources   锁资源
     * @param callable    加锁成功才会执行
     * @param <T>         the callable item type
     * @return callable 的返回值
     * @throws Exception callable exception
     */
    default <T> T lock(String business, long waitTime, long releaseTime, List<Object> resources,
                       Callable<T> callable) throws Exception {
        return lock(null, business, waitTime, releaseTime, resources, callable);
    }

    /**
     * 加分布式锁, 自动释放锁
     *
     * @param prefix    前缀
     * @param business  业务名
     * @param resources 锁资源
     * @param callable  加锁成功才会执行
     * @param <T>       the callable item type
     * @return callable 的返回值
     * @throws Exception callable exception
     */
    default <T> T lock(String prefix, String business, List<Object> resources, Callable<T> callable) throws Exception {
        return lock(LockKeyMaker.makeKey(prefix, business, resources), callable);
    }

    /**
     * 加分布式锁, 自动释放锁
     *
     * @param prefix      前缀
     * @param business    业务名
     * @param waitTime    等待时间
     * @param releaseTime 释放时间
     * @param resources   锁资源
     * @param callable    加锁成功才会执行
     * @param <T>         the callable item type
     * @return callable 的返回值
     * @throws Exception callable exception
     */
    default <T> T lock(String prefix, String business, long waitTime, long releaseTime, List<Object> resources,
                       Callable<T> callable) throws Exception {
        return lock(LockKeyMaker.makeKey(prefix, business, resources), waitTime, releaseTime, callable);
    }
}