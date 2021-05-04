package com.fanxuankai.boot.authorization.server.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.authorization.server.dao.UserDao;
import com.fanxuankai.boot.authorization.server.domain.User;
import com.fanxuankai.boot.authorization.server.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author fanxuankai
 */
@Service
public class UserDaoImpl extends ServiceImpl<UserMapper, User> implements UserDao {
}
