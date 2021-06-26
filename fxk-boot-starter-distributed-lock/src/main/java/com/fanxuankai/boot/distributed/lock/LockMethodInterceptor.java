package com.fanxuankai.boot.distributed.lock;

import com.fanxuankai.boot.distributed.lock.annotation.Lock;
import com.fanxuankai.commons.exception.LockException;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.*;
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
        String key = getKey(lock, methodInvocation);
        if (distributedLocker.lock(key, lock.waitTimeMillis(), lock.releaseTimeMillis())) {
            try {
                return methodInvocation.proceed();
            } finally {
                distributedLocker.unlock(key);
            }
        } else {
            throw new LockException();
        }
    }

    private String getKey(Lock lock, MethodInvocation methodInvocation) {
        String business = getBusiness(lock, methodInvocation);
        return LockKeyMaker.makeKey(lock.prefix(), business, getResources(lock, methodInvocation));
    }

    private String getBusiness(Lock lock, MethodInvocation methodInvocation) {
        String business = lock.business();
        if (!StringUtils.hasText(business)) {
            Method method = methodInvocation.getMethod();
            business = method.getDeclaringClass().getName() + "." + method.getName();
        }
        return business;
    }

    private List<Object> getResources(Lock lock, MethodInvocation methodInvocation) {
        String[] expressions = lock.resources();
        if (expressions.length == 0) {
            return Collections.emptyList();
        }
        Method method = methodInvocation.getMethod();
        Object[] arguments = methodInvocation.getArguments();
        DefaultParameterNameDiscoverer discover = new DefaultParameterNameDiscoverer();
        String[] parameters = discover.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        if (parameters != null && arguments != null && arguments.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                context.setVariable(parameters[i], arguments[i]);
            }
        }
        return Arrays.stream(expressions).map(expression -> {
            SpelExpression spelExpression = parser.parseRaw(expression);
            return Optional.ofNullable(spelExpression.getValue(context))
                    .orElse(null);
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
