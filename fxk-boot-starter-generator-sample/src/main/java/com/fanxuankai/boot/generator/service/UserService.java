package com.fanxuankai.boot.generator.service;

import com.fanxuankai.boot.generator.dto.UserDto;
import com.fanxuankai.boot.generator.dto.UserQueryCriteria;
import com.fanxuankai.boot.generator.vo.UserVo;
import com.fanxuankai.commons.extra.mybatis.base.BaseService;

/**
 * 用户 服务接口
 *
 * @author fanxuankai
 * @date 2021-05-21
 */
public interface UserService extends BaseService<UserDto, UserVo, UserQueryCriteria> {
}