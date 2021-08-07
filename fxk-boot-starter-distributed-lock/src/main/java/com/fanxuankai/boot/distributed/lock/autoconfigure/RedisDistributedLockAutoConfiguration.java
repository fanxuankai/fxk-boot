package com.fanxuankai.boot.distributed.lock.autoconfigure;

import com.fanxuankai.boot.distributed.lock.DistributedLocker;
import com.fanxuankai.boot.distributed.lock.RedisLocker;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author fanxuankai
 */
public class RedisDistributedLockAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public DistributedLocker distributedLocker(RedissonClient redissonClient) {
        return new RedisLocker(redissonClient);
    }
}
