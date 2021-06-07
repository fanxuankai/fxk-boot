package com.fanxuankai.boot.authorization.server;

import com.fanxuankai.boot.authorization.server.autoconfigure.EnableRedisTokenStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fanxuankai
 */
@SpringBootApplication
@EnableRedisTokenStore
public class AuthApp {
    public static void main(String[] args) {
        SpringApplication.run(AuthApp.class, args);
    }
}
