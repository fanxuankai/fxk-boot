package com.fanxuankai.boot.distributed.lock;

import com.fanxuankai.boot.distributed.lock.annotation.Lock;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

/**
 * @author fanxuankai
 */
public class LockPointcutAdvisor extends DefaultPointcutAdvisor {
    public LockPointcutAdvisor(LockMethodInterceptor lockMethodInterceptor) {
        super(AnnotationMatchingPointcut.forMethodAnnotation(Lock.class), lockMethodInterceptor);
    }
}