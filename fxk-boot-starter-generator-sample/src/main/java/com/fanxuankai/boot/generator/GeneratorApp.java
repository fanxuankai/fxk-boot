package com.fanxuankai.boot.generator;

import com.fanxuankai.boot.generator.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fanxuankai
 */
@SpringBootApplication
@MapperScan(basePackageClasses = {UserMapper.class})
public class GeneratorApp {
    public static void main(String[] args) {
        SpringApplication.run(GeneratorApp.class);
    }
}