package com.fanxuankai.boot.admin.autoconfigure;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author fanxuankai
 */
@ComponentScan(basePackages = {"com.fanxuankai.boot.admin"})
@MapperScan(basePackages = {"com.fanxuankai.boot.admin.mapper"})
public class AdminConfiguration {
}
