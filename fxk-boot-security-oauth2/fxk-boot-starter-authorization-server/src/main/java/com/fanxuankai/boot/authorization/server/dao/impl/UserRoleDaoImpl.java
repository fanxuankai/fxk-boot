package com.fanxuankai.boot.authorization.server.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.authorization.server.dao.UserRoleDao;
import com.fanxuankai.boot.authorization.server.domain.UserRole;
import com.fanxuankai.boot.authorization.server.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
 * @author fanxuankai
 */
@Service
public class UserRoleDaoImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleDao {
}
