package com.fanxuankai.boot.log.service;

import com.fanxuankai.boot.log.domain.User;

/**
 * @author fanxuankai
 */
public interface UserService {
    /**
     * 保存用户
     *
     * @param user /
     */
    void save(User user);
}
