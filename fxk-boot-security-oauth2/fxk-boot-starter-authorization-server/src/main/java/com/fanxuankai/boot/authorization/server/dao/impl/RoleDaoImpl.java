package com.fanxuankai.boot.authorization.server.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.authorization.server.dao.RoleDao;
import com.fanxuankai.boot.authorization.server.domain.Role;
import com.fanxuankai.boot.authorization.server.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
 * @author fanxuankai
 */
@Service
public class RoleDaoImpl extends ServiceImpl<RoleMapper, Role> implements RoleDao {
}
