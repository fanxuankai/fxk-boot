package com.fanxuankai.boot.distributed.lock.autoconfigure;

import com.fanxuankai.boot.distributed.lock.DistributedLocker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author fanxuankai
 */
public class LockAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({DistributedLocker.class})
    public LockMethodInterceptor lockMethodInterceptor(DistributedLocker distributedLocker) {
        return new LockMethodInterceptor(distributedLocker);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({LockMethodInterceptor.class})
    public LockPointcutAdvisor lockPointcutAdvisor(LockMethodInterceptor lockMethodInterceptor) {
        return new LockPointcutAdvisor(lockMethodInterceptor);
    }

}
