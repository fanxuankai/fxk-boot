package com.fanxuankai.boot.data.tree.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.data.tree.dao.UserPathTreeDao;
import com.fanxuankai.boot.data.tree.domain.UserPathTree;
import com.fanxuankai.boot.data.tree.mapper.UserPathTreeMapper;
import org.springframework.stereotype.Service;

/**
 * @author fanxuankai
 */
@Service
public class UserPathTreeDaoImpl extends ServiceImpl<UserPathTreeMapper, UserPathTree> implements UserPathTreeDao {
}