package com.fanxuankai.boot.data.tree.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.data.tree.dao.UserPathEnumerationsDao;
import com.fanxuankai.boot.data.tree.domain.UserPathEnumerations;
import com.fanxuankai.boot.data.tree.mapper.UserPathEnumerationsMapper;
import org.springframework.stereotype.Service;

/**
 * @author fanxuankai
 */
@Service
public class UserPathEnumerationsDaoImpl extends ServiceImpl<UserPathEnumerationsMapper, UserPathEnumerations>
        implements UserPathEnumerationsDao {
}
