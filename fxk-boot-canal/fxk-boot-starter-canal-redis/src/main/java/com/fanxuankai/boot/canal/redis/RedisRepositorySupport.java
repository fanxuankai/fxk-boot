package com.fanxuankai.boot.canal.redis;

import com.fanxuankai.boot.canal.redis.repository.RedisRepository;
import com.fanxuankai.boot.canal.redis.repository.SimpleRedisRepository;
import com.fanxuankai.canal.core.util.GenericTypeUtils;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.data.projection.DefaultMethodInvokingMethodInterceptor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;

import javax.annotation.Resource;

/**
 * @author fanxuankai
 */
public class RedisRepositorySupport<T, ID> implements FactoryBean<T>, BeanClassLoaderAware {

    private final Class<? extends T> redisRepositoryInterface;
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;
    private ClassLoader classLoader;

    public RedisRepositorySupport(Class<T> redisRepositoryInterface) {
        this.redisRepositoryInterface = redisRepositoryInterface;
    }

    /**
     * @return T
     * @see org.springframework.data.repository.core.support.RepositoryFactorySupport#getRepository(Class, org.springframework.data.repository.core.support.RepositoryComposition.RepositoryFragments)
     */
    @Override
    @SuppressWarnings("unchecked")
    public T getObject() {
        Class<T> domainType = GenericTypeUtils.getGenericType(redisRepositoryInterface, RedisRepository.class, "T");
        ProxyFactory factory = new ProxyFactory();
        factory.setTarget(new SimpleRedisRepository<>(domainType, redisTemplate));
        factory.setInterfaces(redisRepositoryInterface);
        if (DefaultMethodInvokingMethodInterceptor.hasDefaultMethods(redisRepositoryInterface)) {
            factory.addAdvice(new DefaultMethodInvokingMethodInterceptor());
        }
        return (T) factory.getProxy(classLoader);
    }

    @Override
    public Class<?> getObjectType() {
        return redisRepositoryInterface;
    }

    @Override
    public void setBeanClassLoader(@NonNull ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
