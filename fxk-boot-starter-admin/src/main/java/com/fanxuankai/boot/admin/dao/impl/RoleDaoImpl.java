package com.fanxuankai.boot.admin.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.admin.dao.RoleDao;
import com.fanxuankai.boot.admin.mapper.RoleMapper;
import com.fanxuankai.boot.admin.model.Role;
import org.springframework.stereotype.Service;

/**
 * 角色 数据访问实现类
 *
 * @author fanxuankai
 * @date 2021-06-04
 */
@Service
public class RoleDaoImpl extends ServiceImpl<RoleMapper, Role> implements RoleDao {
}