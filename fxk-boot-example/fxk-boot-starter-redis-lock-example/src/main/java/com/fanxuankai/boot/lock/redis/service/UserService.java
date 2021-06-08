package com.fanxuankai.boot.lock.redis.service;

import com.fanxuankai.boot.lock.redis.domian.User;

/**
 * @author fanxuankai
 */
public interface UserService {

    /**
     * 登录
     *
     * @param user 用户
     */
    void login(User user);

}
