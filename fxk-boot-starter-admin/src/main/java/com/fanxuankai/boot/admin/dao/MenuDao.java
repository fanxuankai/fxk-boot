package com.fanxuankai.boot.admin.dao;

import com.fanxuankai.boot.admin.dto.MenuQueryCriteria;
import com.fanxuankai.boot.admin.model.Menu;
import com.fanxuankai.commons.extra.mybatis.base.BaseDao;

/**
 * 菜单 数据访问接口
 *
 * @author fanxuankai
 */
public interface MenuDao extends BaseDao<Menu, MenuQueryCriteria> {
}