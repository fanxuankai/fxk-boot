package com.fanxuankai.boot.admin.service;

import com.fanxuankai.boot.admin.dto.MenuDTO;
import com.fanxuankai.boot.admin.dto.MenuQueryCriteria;
import com.fanxuankai.boot.admin.vo.MenuVO;
import com.fanxuankai.commons.extra.mybatis.base.BaseService;

/**
 * 菜单 服务接口
 *
 * @author fanxuankai
 */
public interface MenuService extends BaseService<MenuDTO, MenuVO, MenuQueryCriteria> {
}