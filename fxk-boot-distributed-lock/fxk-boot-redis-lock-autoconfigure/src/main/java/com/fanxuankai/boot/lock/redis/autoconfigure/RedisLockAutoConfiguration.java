package com.fanxuankai.boot.lock.redis.autoconfigure;

import com.fanxuankai.boot.distributed.lock.DistributedLocker;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author fanxuankai
 */
public class RedisLockAutoConfiguration {
    @Bean(name = "com.fanxuankai.boot.distributed.lock.DistributedLocker")
    @ConditionalOnMissingBean({DistributedLocker.class})
    @ConditionalOnBean({RedissonClient.class})
    public DistributedLocker distributedLocker(RedissonClient redissonClient) {
        return new RedisLocker(redissonClient);
    }
}
