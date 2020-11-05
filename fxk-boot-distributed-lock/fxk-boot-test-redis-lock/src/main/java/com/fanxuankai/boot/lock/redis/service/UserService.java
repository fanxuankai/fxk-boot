package com.fanxuankai.boot.lock.redis.service;

import com.fanxuankai.boot.lock.redis.domian.User;

/**
 * @author fanxuankai
 */
public interface UserService {

    void login(User user);

}
