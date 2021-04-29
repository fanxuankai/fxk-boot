package com.fanxuankai.boot.mybatis.plus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author fanxuankai
 */
public interface InsertBatchBaseMapper<T> extends BaseMapper<T> {
    /**
     * 批量插入 仅适用于mysql
     *
     * @param entityList 实体列表
     * @return 影响行数
     */
    int insertBatchSomeColumn(Iterable<T> entityList);
}
