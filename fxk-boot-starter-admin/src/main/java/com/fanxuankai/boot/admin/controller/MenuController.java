package com.fanxuankai.boot.admin.controller;

import com.fanxuankai.boot.admin.dto.MenuDTO;
import com.fanxuankai.boot.admin.service.MenuService;
import com.fanxuankai.boot.admin.vo.MenuVO;
import com.fanxuankai.commons.extra.spring.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单 管理
 *
 * @author fanxuankai
 * @date 2021-06-04
 */
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController<MenuDTO, MenuVO, MenuService> {
}