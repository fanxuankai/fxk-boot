package com.fanxuankai.boot.admin.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.admin.dao.MenuDao;
import com.fanxuankai.boot.admin.mapper.MenuMapper;
import com.fanxuankai.boot.admin.model.Menu;
import org.springframework.stereotype.Service;

/**
 * 菜单 数据访问实现类
 *
 * @author fanxuankai
 * @date 2021-06-04
 */
@Service
public class MenuDaoImpl extends ServiceImpl<MenuMapper, Menu> implements MenuDao {
}