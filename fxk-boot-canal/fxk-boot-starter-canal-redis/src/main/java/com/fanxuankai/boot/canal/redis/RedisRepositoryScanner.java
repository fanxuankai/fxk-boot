package com.fanxuankai.boot.canal.redis;

import cn.hutool.core.util.ClassUtil;
import com.fanxuankai.boot.canal.redis.repository.RedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
public class RedisRepositoryScanner {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisRepositoryScanner.class);

    public static Set<Class<?>> scan(List<String> basePackages) {
        StopWatch sw = new StopWatch();
        sw.start();
        Set<Class<?>> redisRepositories = basePackages.stream()
                .flatMap(basePackage -> ClassUtil.scanPackageBySuper(basePackage, RedisRepository.class).stream())
                .collect(Collectors.toSet());
        sw.stop();
        String simpleName = RedisRepository.class.getSimpleName();
        LOGGER.info("Finished {} scanning in {}ms. Found {} {} interfaces.", simpleName, sw.getTotalTimeMillis(),
                redisRepositories.size(), simpleName);
        return redisRepositories;
    }

}
