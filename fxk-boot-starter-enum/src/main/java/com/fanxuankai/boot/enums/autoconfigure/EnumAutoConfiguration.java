package com.fanxuankai.boot.enums.autoconfigure;

import com.fanxuankai.boot.enums.EnumGenerator;
import com.fanxuankai.boot.enums.mapper.EnumMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author fanxuankai
 */
@Configuration
@MapperScan(basePackageClasses = EnumMapper.class)
@ComponentScan(basePackageClasses = EnumGenerator.class)
@EnableTransactionManagement
public class EnumAutoConfiguration {
}