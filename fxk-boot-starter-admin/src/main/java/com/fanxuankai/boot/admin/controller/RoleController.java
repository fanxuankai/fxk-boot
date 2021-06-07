package com.fanxuankai.boot.admin.controller;

import com.fanxuankai.boot.admin.dto.RoleDTO;
import com.fanxuankai.boot.admin.service.RoleService;
import com.fanxuankai.boot.admin.vo.RoleVO;
import com.fanxuankai.commons.extra.spring.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色 管理
 *
 * @author fanxuankai
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController<RoleDTO, RoleVO, RoleService> {
}