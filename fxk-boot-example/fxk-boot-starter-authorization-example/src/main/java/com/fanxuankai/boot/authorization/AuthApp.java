package com.fanxuankai.boot.authorization;

import com.fanxuankai.boot.authorization.annotation.EnableRedisTokenStore;
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
