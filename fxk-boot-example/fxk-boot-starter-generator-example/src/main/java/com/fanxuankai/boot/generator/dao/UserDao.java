package com.fanxuankai.boot.generator.dao;

import com.fanxuankai.boot.generator.model.User;
import com.fanxuankai.boot.generator.dto.UserQueryCriteria;
import com.fanxuankai.commons.extra.mybatis.base.BaseDao;

/**
 * 用户 数据访问接口
 *
 * @author admin
 */
public interface UserDao extends BaseDao<User, UserQueryCriteria> {
}