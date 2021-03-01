package com.fanxuankai.boot.mybatis.plus.core.annotation.strategy;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;

/**
 * Query 处理器
 *
 * @author fanxuankai
 */
public interface QueryHandler {
    /**
     * 注册
     *
     * @param registry /
     */
    void register(QueryHandlerRegistry registry);

    /**
     * 处理
     *
     * @param wrapper /
     * @param column  /
     * @param val     /
     */
    void handle(AbstractWrapper<?, String, ?> wrapper, String column, Object val);
}
