package com.fanxuankai.boot.admin.controller;

import com.fanxuankai.boot.admin.dto.UserDTO;
import com.fanxuankai.boot.admin.dto.UserQueryCriteria;
import com.fanxuankai.boot.admin.service.UserService;
import com.fanxuankai.boot.admin.vo.UserVO;
import com.fanxuankai.commons.extra.spring.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户 管理
 *
 * @author fanxuankai
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<UserDTO, UserVO, UserQueryCriteria, UserService> {
}