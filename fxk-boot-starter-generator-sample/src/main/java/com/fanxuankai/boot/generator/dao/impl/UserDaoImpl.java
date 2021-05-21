package com.fanxuankai.boot.generator.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.generator.dao.UserDao;
import com.fanxuankai.boot.generator.mapper.UserMapper;
import com.fanxuankai.boot.generator.model.User;
import org.springframework.stereotype.Service;

/**
 * 用户 数据访问实现类
 *
 * @author fanxuankai
 * @date 2021-05-21
 */
@Service
public class UserDaoImpl extends ServiceImpl<UserMapper, User> implements UserDao {
}