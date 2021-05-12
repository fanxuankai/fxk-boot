package com.fanxuankai.boot.data.tree;

import com.fanxuankai.boot.data.tree.mapper.TreeNodeMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fanxuankai
 */
@SpringBootApplication
@MapperScan(basePackageClasses = {TreeNodeMapper.class})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
