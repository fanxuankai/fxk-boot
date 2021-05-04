package com.fanxuankai.boot.authorization.server.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.authorization.server.dao.PermissionDao;
import com.fanxuankai.boot.authorization.server.domain.Permission;
import com.fanxuankai.boot.authorization.server.mapper.PermissionMapper;
import org.springframework.stereotype.Service;

/**
 * @author fanxuankai
 */
@Service
public class PermissionDaoImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionDao {
}
