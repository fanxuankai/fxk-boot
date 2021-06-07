package com.fanxuankai.boot.resource.server;

import com.fanxuankai.boot.resource.server.autoconfigure.EnableRedisTokenStore;
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