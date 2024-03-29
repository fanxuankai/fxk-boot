package com.fanxuankai.boot.admin.service;

import com.fanxuankai.boot.admin.dto.RoleDTO;
import com.fanxuankai.boot.admin.dto.RoleQueryCriteria;
import com.fanxuankai.boot.admin.vo.RoleVO;
import com.fanxuankai.commons.extra.mybatis.base.BaseService;

/**
 * 角色 服务接口
 *
 * @author fanxuankai
 */
public interface RoleService extends BaseService<RoleDTO, RoleVO, RoleQueryCriteria> {
}