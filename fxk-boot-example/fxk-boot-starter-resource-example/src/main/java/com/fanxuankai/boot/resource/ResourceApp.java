package com.fanxuankai.boot.resource;

import com.fanxuankai.boot.resource.annotation.EnableRedisTokenStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fanxuankai
 */
@SpringBootApplication
@EnableRedisTokenStore
public class ResourceApp {
    public static void main(String[] args) {
        SpringApplication.run(ResourceApp.class, args);
    }
}