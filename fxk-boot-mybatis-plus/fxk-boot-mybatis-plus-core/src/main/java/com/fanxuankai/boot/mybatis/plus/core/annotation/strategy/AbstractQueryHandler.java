package com.fanxuankai.boot.mybatis.plus.core.annotation.strategy;

import com.fanxuankai.boot.mybatis.plus.core.annotation.Query;

/**
 * @author fanxuankai
 */
public abstract class AbstractQueryHandler implements QueryHandler {
    /**
     * 对应的处理方式
     *
     * @return /
     */
    protected abstract Query.Type getType();

    @Override
    public void register(QueryHandlerRegistry registry) {
        registry.registry(getType(), this);
    }
}
