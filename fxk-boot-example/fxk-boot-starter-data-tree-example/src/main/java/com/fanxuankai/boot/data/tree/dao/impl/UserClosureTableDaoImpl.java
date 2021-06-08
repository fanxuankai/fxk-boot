package com.fanxuankai.boot.data.tree.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.data.tree.dao.UserClosureTableDao;
import com.fanxuankai.boot.data.tree.domain.UserClosureTable;
import com.fanxuankai.boot.data.tree.mapper.UserClosureTableMapper;
import org.springframework.stereotype.Service;

/**
 * @author fanxuankai
 */
@Service
public class UserClosureTableDaoImpl extends ServiceImpl<UserClosureTableMapper, UserClosureTable>
        implements UserClosureTableDao {
}
