package com.fanxuankai.boot.mybatis.plus.core.annotation.strategy;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.fanxuankai.boot.mybatis.plus.core.annotation.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author fanxuankai
 */
public class BetweenQueryHandler extends AbstractQueryHandler {
    @Override
    public void handle(AbstractWrapper<?, String, ?> wrapper, String column, Object val) {
        if (val instanceof Collection) {
            List<?> betweenVal = new ArrayList<>((Collection<?>) val);
            wrapper.between(column, betweenVal.get(0), betweenVal.get(1));
        }
    }

    @Override
    protected Query.Type getType() {
        return Query.Type.BETWEEN;
    }
}
