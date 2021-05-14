package com.fanxuankai.boot.distributed.lock.autoconfigure;

import com.fanxuankai.boot.distributed.lock.DistributedLocker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author fanxuankai
 */
public class LockAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public LockPointcutAdvisor lockPointcutAdvisor(DistributedLocker distributedLocker) {
        return new LockPointcutAdvisor(new LockMethodInterceptor(distributedLocker));
    }
}
