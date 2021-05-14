package com.fanxuankai.boot.data.tree.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.data.tree.dao.UserTreeDao;
import com.fanxuankai.boot.data.tree.domain.UserTree;
import com.fanxuankai.boot.data.tree.mapper.UserTreeMapper;
import org.springframework.stereotype.Service;

/**
 * @author fanxuankai
 */
@Service
public class UserTreeDaoImpl extends ServiceImpl<UserTreeMapper, UserTree> implements UserTreeDao {
}