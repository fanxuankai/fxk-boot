package com.fanxuankai.boot.admin.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.admin.dao.UserRoleDao;
import com.fanxuankai.boot.admin.mapper.UserRoleMapper;
import com.fanxuankai.boot.admin.model.UserRole;
import org.springframework.stereotype.Service;

/**
 * 用户-角色 数据访问实现类
 *
 * @author fanxuankai
 */
@Service
public class UserRoleDaoImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleDao {
}