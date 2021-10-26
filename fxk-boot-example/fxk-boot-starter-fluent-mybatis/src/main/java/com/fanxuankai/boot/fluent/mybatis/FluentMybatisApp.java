package com.fanxuankai.boot.fluent.mybatis;

import com.fanxuankai.boot.fluent.mybatis.mapper.StudentScoreMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fanxuankai
 */
@SpringBootApplication
@MapperScan(basePackageClasses = {StudentScoreMapper.class})
public class FluentMybatisApp {
    public static void main(String[] args) {
        SpringApplication.run(FluentMybatisApp.class, args);
    }
}
