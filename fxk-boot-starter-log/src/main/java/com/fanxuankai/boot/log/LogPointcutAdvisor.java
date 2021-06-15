package com.fanxuankai.boot.log;

import com.fanxuankai.boot.log.annotation.Log;
import org.aopalliance.aop.Advice;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

/**
 * @author fanxuankai
 */
public class LogPointcutAdvisor extends DefaultPointcutAdvisor {
    public LogPointcutAdvisor(Advice advice) {
        super(AnnotationMatchingPointcut.forMethodAnnotation(Log.class), advice);
    }
}