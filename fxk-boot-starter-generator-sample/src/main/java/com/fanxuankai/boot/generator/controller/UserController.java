package com.fanxuankai.boot.generator.controller;

import com.fanxuankai.boot.generator.dto.UserDTO;
import com.fanxuankai.boot.generator.dto.UserQueryCriteria;
import com.fanxuankai.boot.generator.service.UserService;
import com.fanxuankai.boot.generator.vo.UserVO;
import com.fanxuankai.commons.extra.spring.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户 管理
 *
 * @author fanxuankai
 * @date 2021-05-21
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController<UserDTO, UserVO, UserQueryCriteria, UserService> {
}