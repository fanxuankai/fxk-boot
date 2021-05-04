package com.fanxuankai.boot.authorization.server.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.authorization.server.dao.RolePermissionDao;
import com.fanxuankai.boot.authorization.server.domain.RolePermission;
import com.fanxuankai.boot.authorization.server.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;

/**
 * @author fanxuankai
 */
@Service
public class RolePermissionDaoImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionDao {
}
