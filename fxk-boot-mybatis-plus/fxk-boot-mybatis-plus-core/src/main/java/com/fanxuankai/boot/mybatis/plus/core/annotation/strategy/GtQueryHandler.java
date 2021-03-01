package com.fanxuankai.boot.mybatis.plus.core.annotation.strategy;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.fanxuankai.boot.mybatis.plus.core.annotation.Query;

/**
 * @author fanxuankai
 */
public class GtQueryHandler extends AbstractQueryHandler {
    @Override
    public void handle(AbstractWrapper<?, String, ?> wrapper, String column, Object val) {
        wrapper.gt(column, val);
    }

    @Override
    protected Query.Type getType() {
        return Query.Type.GT;
    }
}
