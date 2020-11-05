package com.fanxuankai.boot.distributed.lock.autoconfigure;

import com.fanxuankai.boot.distributed.lock.DistributedLocker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author fanxuankai
 */
public class LockAutoConfiguration {

    @Bean(name = "com.fanxuankai.boot.distributed.lock.config.LockMethodInterceptor")
    @ConditionalOnMissingBean
    public LockMethodInterceptor lockMethodInterceptor(@Qualifier("com.fanxuankai.boot.distributed.lock" +
            ".DistributedLocker") DistributedLocker distributedLocker) {
        return new LockMethodInterceptor(distributedLocker);
    }

    @Bean
    @ConditionalOnMissingBean
    public LockPointcutAdvisor lockPointcutAdvisor(@Qualifier("com.fanxuankai.boot.distributed.lock.config" +
            ".LockMethodInterceptor") LockMethodInterceptor lockMethodInterceptor) {
        return new LockPointcutAdvisor(lockMethodInterceptor);
    }

}
