package com.fanxuankai.boot.data.tree.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.data.tree.dao.UserAdjacencyListDao;
import com.fanxuankai.boot.data.tree.domain.UserAdjacencyList;
import com.fanxuankai.boot.data.tree.mapper.UserAdjacencyListMapper;
import org.springframework.stereotype.Service;

/**
 * @author fanxuankai
 */
@Service
public class UserAdjacencyListDaoImpl extends ServiceImpl<UserAdjacencyListMapper, UserAdjacencyList> implements UserAdjacencyListDao {
}
