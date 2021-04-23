package com.fanxuankai.boot.canal.redis.config;

import com.fanxuankai.canal.redis.CanalRedisWorker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author fanxuankai
 */
@EnableConfigurationProperties(CanalRedisProperties.class)
public class CanalRedisAutoConfiguration {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Bean
    @ConditionalOnProperty(prefix = CanalRedisProperties.PREFIX, name = "enabled", havingValue = "true")
    @ConditionalOnMissingBean
    public CanalRedisWorker canalRedisWorker(CanalRedisProperties canalRedisProperties) {
        return CanalRedisWorker.newCanalWorker(canalRedisProperties.getConfiguration(), canalRedisProperties,
                redisTemplate);
    }

}
