package com.fanxuankai.boot.mybatis.plus.core.annotation.strategy;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.fanxuankai.boot.mybatis.plus.core.annotation.Query;

import java.util.Collection;

/**
 * @author fanxuankai
 */
public class NotInQueryHandler extends AbstractQueryHandler {
    @Override
    public void handle(AbstractWrapper<?, String, ?> wrapper, String column, Object val) {
        if (val instanceof Collection) {
            wrapper.notIn(column, (Collection<?>) val);
        }
    }

    @Override
    protected Query.Type getType() {
        return Query.Type.NOT_IN;
    }
}
