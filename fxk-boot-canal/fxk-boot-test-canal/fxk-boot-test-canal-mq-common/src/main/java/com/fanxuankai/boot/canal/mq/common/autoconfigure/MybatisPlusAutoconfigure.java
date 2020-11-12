package com.fanxuankai.boot.canal.mq.common.autoconfigure;

import com.fanxuankai.boot.canal.mq.common.mapper.UserMapper;
import com.fanxuankai.boot.canal.mq.common.service.impl.UserServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author fanxuankai
 */
@ConditionalOnClass(MapperScan.class)
@MapperScan(basePackageClasses = {UserMapper.class})
@ComponentScan(basePackageClasses = {UserServiceImpl.class})
public class MybatisPlusAutoconfigure {
}
