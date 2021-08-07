package com.fanxuankai.boot.canal.mq.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.canal.mq.common.domain.User;
import com.fanxuankai.boot.canal.mq.common.mapper.UserMapper;
import com.fanxuankai.boot.canal.mq.common.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author fanxuankai
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
