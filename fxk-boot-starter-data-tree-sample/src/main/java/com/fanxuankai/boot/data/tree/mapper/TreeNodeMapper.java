package com.fanxuankai.boot.data.tree.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fanxuankai.boot.data.tree.domain.UserTree;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author fanxuankai
 */
@Mapper
public interface TreeNodeMapper extends BaseMapper<UserTree> {
}
