package com.fanxuankai.boot.admin.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.admin.dao.RoleMenuDao;
import com.fanxuankai.boot.admin.mapper.RoleMenuMapper;
import com.fanxuankai.boot.admin.model.RoleMenu;
import org.springframework.stereotype.Service;

/**
 * 角色-菜单 数据访问实现类
 *
 * @author fanxuankai
 * @date 2021-06-04
 */
@Service
public class RoleMenuDaoImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuDao {
}