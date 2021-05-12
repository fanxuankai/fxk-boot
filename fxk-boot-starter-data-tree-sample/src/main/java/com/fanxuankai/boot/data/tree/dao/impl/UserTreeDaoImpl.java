package com.fanxuankai.boot.data.tree.dao.impl;

import com.fanxuankai.boot.data.tree.dao.UserTreeDao;
import com.fanxuankai.boot.data.tree.domain.UserTree;
import com.fanxuankai.boot.data.tree.mapper.TreeNodeMapper;
import com.fanxuankai.commons.extra.mybatis.tree.SimpleTreeDao;
import org.springframework.stereotype.Service;

/**
 * @author fanxuankai
 */
@Service
public class UserTreeDaoImpl extends SimpleTreeDao<UserTree, TreeNodeMapper, Object> implements UserTreeDao {
}
