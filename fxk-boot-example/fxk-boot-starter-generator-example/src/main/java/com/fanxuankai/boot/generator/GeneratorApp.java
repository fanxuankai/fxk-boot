package com.fanxuankai.boot.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fanxuankai
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.fanxuankai.boot.generator.mapper"})
public class GeneratorApp {
    public static void main(String[] args) {
        SpringApplication.run(GeneratorApp.class);
    }
}