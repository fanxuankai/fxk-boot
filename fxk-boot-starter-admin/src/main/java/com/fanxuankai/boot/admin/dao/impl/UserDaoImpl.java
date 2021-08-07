package com.fanxuankai.boot.admin.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.admin.dao.UserDao;
import com.fanxuankai.boot.admin.mapper.UserMapper;
import com.fanxuankai.boot.admin.model.User;
import org.springframework.stereotype.Service;

/**
 * 用户 数据访问实现类
 *
 * @author fanxuankai
 */
@Service
public class UserDaoImpl extends ServiceImpl<UserMapper, User> implements UserDao {
}