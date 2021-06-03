package com.fanxuankai.boot.authorization.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fanxuankai.boot.authorization.server.domain.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author fanxuankai
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
