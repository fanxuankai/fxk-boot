package com.fanxuankai.boot.data.tree.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.data.tree.dao.UserFastTreeDao;
import com.fanxuankai.boot.data.tree.domain.UserFastTree;
import com.fanxuankai.boot.data.tree.mapper.UserFastTreeMapper;
import org.springframework.stereotype.Service;

/**
 * @author fanxuankai
 */
@Service
public class UserFastTreeDaoImpl extends ServiceImpl<UserFastTreeMapper, UserFastTree> implements UserFastTreeDao {
}
