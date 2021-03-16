package com.fanxuankai.boot.mybatis.plus.core.annotation.strategy;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;

/**
 * 查询条件封装器
 *
 * @author fanxuankai
 */
public class QueryCriteriaWrapper {
    private WrapBehavior wrapBehavior;

    /**
     * 封装
     *
     * @param wrapper 封装类
     * @param column  列
     * @param val     值
     */
    public void wrap(AbstractWrapper<?, String, ?> wrapper, String column, Object val) {
        if (wrapBehavior == null) {
            return;
        }
        wrapBehavior.wrap(wrapper, column, val);
    }

    public void setWrapBehavior(WrapBehavior wrapBehavior) {
        this.wrapBehavior = wrapBehavior;
    }
}
