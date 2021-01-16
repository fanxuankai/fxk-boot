package com.fanxuankai.boot.distributed.lock.autoconfigure;

import com.fanxuankai.boot.distributed.lock.DistributedLocker;
import com.fanxuankai.boot.distributed.lock.LockKeyMaker;
import com.fanxuankai.boot.distributed.lock.annotation.Lock;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
public class LockMethodInterceptor implements MethodInterceptor {

    private final DistributedLocker distributedLocker;
    private final SpelExpressionParser parser = new SpelExpressionParser();

    public LockMethodInterceptor(DistributedLocker distributedLocker) {
        this.distributedLocker = distributedLocker;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Lock lock = methodInvocation.getMethod().getAnnotation(Lock.class);
        AtomicBoolean proceedError = new AtomicBoolean();
        Object result = distributedLocker.lock(getKey(lock, methodInvocation), lock.waitTimeMillis(),
                lock.releaseTimeMillis(), () -> {
                    try {
                        return methodInvocation.proceed();
                    } catch (Throwable throwable) {
                        proceedError.set(true);
                        return throwable;
                    }
                });
        if (proceedError.get() && result instanceof Throwable) {
            throw (Throwable) result;
        }
        return result;
    }

    private String getKey(Lock lock, MethodInvocation methodInvocation) {
        String business = getBusiness(lock, methodInvocation);
        return LockKeyMaker.makeKey(lock.prefix(), business, getResources(lock, methodInvocation));
    }

    private String getBusiness(Lock lock, MethodInvocation methodInvocation) {
        String business = lock.business();
        if (StringUtils.isEmpty(business)) {
            Method method = methodInvocation.getMethod();
            business = method.getDeclaringClass().getName() + "." + method.getName();
        }
        return business;
    }

    private List<Object> getResources(Lock lock, MethodInvocation methodInvocation) {
        String[] expressions = lock.resources();
        if (StringUtils.isEmpty(expressions)) {
            return Collections.emptyList();
        }
        Method method = methodInvocation.getMethod();
        Object[] arguments = methodInvocation.getArguments();
        Parameter[] parameters = method.getParameters();
        EvaluationContext context = new StandardEvaluationContext();
        if (arguments != null && arguments.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                context.setVariable(parameters[i].getName(), arguments[i]);
            }
        }
        return Arrays.stream(expressions).map(expression -> {
            SpelExpression spelExpression = parser.parseRaw(expression);
            return Optional.ofNullable(spelExpression.getValue(context))
                    .orElse(null);
        })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
