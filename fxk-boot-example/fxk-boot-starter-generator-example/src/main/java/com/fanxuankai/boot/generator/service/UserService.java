package com.fanxuankai.boot.generator.service;

import com.fanxuankai.boot.generator.dto.UserDTO;
import com.fanxuankai.boot.generator.dto.UserQueryCriteria;
import com.fanxuankai.boot.generator.vo.UserVO;
import com.fanxuankai.commons.extra.mybatis.base.BaseService;

/**
 * 用户 服务接口
 *
 * @author admin
 */
public interface UserService extends BaseService<UserDTO, UserVO, UserQueryCriteria> {
}