package com.fanxuankai.boot.distributed.lock.autoconfigure;

import com.fanxuankai.boot.distributed.lock.DistributedLocker;
import com.fanxuankai.boot.distributed.lock.LockMethodInterceptor;
import com.fanxuankai.boot.distributed.lock.LockPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author fanxuankai
 */
@Configuration
@Import({RedisDistributedLockAutoConfiguration.class})
public class DistributedLockAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public LockPointcutAdvisor lockPointcutAdvisor(DistributedLocker distributedLocker) {
        return new LockPointcutAdvisor(new LockMethodInterceptor(distributedLocker));
    }
}
