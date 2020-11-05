package com.fanxuankai.boot.canal.redis;

import com.fanxuankai.boot.canal.redis.annotation.RedisRepositoryScan;
import com.fanxuankai.boot.canal.redis.repository.UserRedisRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fanxuankai
 */
@SpringBootApplication
@RedisRepositoryScan(basePackageClasses = {UserRedisRepository.class})
public class CanalRedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(CanalRedisApplication.class, args);
    }
}
