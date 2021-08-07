package com.fanxuankai.boot.mp;

import com.fanxuankai.boot.mp.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fanxuankai
 */
@SpringBootApplication
@MapperScan(basePackageClasses = {UserMapper.class})
public class MpApp {
    public static void main(String[] args) {
        SpringApplication.run(MpApp.class, args);
    }
}
