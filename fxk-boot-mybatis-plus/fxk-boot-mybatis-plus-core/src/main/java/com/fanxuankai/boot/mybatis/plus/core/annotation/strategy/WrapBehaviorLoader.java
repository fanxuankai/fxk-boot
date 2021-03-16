package com.fanxuankai.boot.mybatis.plus.core.annotation.strategy;

import com.fanxuankai.boot.mybatis.plus.core.annotation.Query;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * 查询条件封装行为加载器
 *
 * @author fanxuankai
 */
public class WrapBehaviorLoader {
    private static final Map<Query.Type, WrapBehavior> WRAP_BEHAVIOR_MAP;

    static {
        WRAP_BEHAVIOR_MAP = new HashMap<>();
        ServiceLoader<AbstractWrapBehavior> loader = ServiceLoader.load(AbstractWrapBehavior.class);
        loader.forEach(behavior -> WRAP_BEHAVIOR_MAP.put(behavior.getType(), behavior));
    }

    public static WrapBehavior get(Query.Type type) {
        return WRAP_BEHAVIOR_MAP.get(type);
    }
}
