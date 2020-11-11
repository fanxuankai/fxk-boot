package com.fanxuankai.boot.canal.mq.common.autoconfigure;

import com.fanxuankai.boot.canal.mq.common.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;

/**
 * @author fanxuankai
 */
@MapperScan(basePackageClasses = {UserMapper.class})
public class MybatisPlusAutoconfigure {
}
