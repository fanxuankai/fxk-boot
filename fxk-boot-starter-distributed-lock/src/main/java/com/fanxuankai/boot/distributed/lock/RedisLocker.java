package com.fanxuankai.boot.distributed.lock;

import com.fanxuankai.boot.distributed.lock.exception.LockException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 基于 Redis 实现的分布式锁
 *
 * @author fanxuankai
 */
public class RedisLocker implements DistributedLocker {
    private final RedissonClient redissonClient;

    public RedisLocker(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public boolean lock(String key, long waitTime, long releaseTime) {
        return tryLock(key, waitTime, releaseTime);
    }

    @Override
    public void unlock(String key) {
        RLock lock = redissonClient.getLock(key);
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    @Override
    public <T> T lock(String key, long waitTime, long releaseTime, Callable<T> callable) throws Exception {
        RLock lock = redissonClient.getLock(key);
        if (tryLock(lock, waitTime, releaseTime)) {
            try {
                return callable.call();
            } finally {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        } else {
            throw new LockException();
        }
    }

    @Override
    public void lock(String key, long waitTime, long releaseTime, Runnable runnable) {
        RLock lock = redissonClient.getLock(key);
        if (tryLock(lock, waitTime, releaseTime)) {
            try {
                runnable.run();
            } finally {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        } else {
            throw new LockException();
        }
    }

    private boolean tryLock(RLock lock, long waitTime, long releaseTime) {
        try {
            return lock.tryLock(waitTime, releaseTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    private boolean tryLock(String key, long waitTime, long releaseTime) {
        RLock lock = redissonClient.getLock(key);
        try {
            return lock.tryLock(waitTime, releaseTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

}