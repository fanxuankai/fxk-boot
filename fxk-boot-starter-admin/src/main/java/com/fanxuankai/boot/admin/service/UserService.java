package com.fanxuankai.boot.admin.service;

import com.fanxuankai.boot.admin.dto.UserDTO;
import com.fanxuankai.boot.admin.dto.UserQueryCriteria;
import com.fanxuankai.boot.admin.vo.UserVO;
import com.fanxuankai.commons.extra.mybatis.base.BaseService;

/**
 * 用户 服务接口
 *
 * @author fanxuankai
 */
public interface UserService extends BaseService<UserDTO, UserVO, UserQueryCriteria> {
}