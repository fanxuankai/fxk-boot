package com.fanxuankai.boot.distributed.lock.service;

import com.fanxuankai.boot.distributed.lock.domian.User;

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
