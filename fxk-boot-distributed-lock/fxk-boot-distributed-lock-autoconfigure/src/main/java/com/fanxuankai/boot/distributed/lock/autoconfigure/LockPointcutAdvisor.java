package com.fanxuankai.boot.distributed.lock.autoconfigure;

import com.fanxuankai.boot.distributed.lock.annotation.Lock;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.lang.NonNull;

/**
 * @author fanxuankai
 */
public class LockPointcutAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {
    private final Advice advice;
    private final Pointcut pointcut;

    public LockPointcutAdvisor(@NonNull LockMethodInterceptor interceptor) {
        this.advice = interceptor;
        this.pointcut = AnnotationMatchingPointcut.forMethodAnnotation(Lock.class);
    }

    @Override
    @NonNull
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    @NonNull
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        if (this.advice instanceof BeanFactoryAware) {
            ((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
        }
    }
}