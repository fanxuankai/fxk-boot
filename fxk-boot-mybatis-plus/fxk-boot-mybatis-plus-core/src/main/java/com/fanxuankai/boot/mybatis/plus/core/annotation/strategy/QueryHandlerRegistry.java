package com.fanxuankai.boot.mybatis.plus.core.annotation.strategy;

import com.fanxuankai.boot.mybatis.plus.core.annotation.Query;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * @author fanxuankai
 */
public class QueryHandlerRegistry {
    public Map<Query.Type, QueryHandler> handlerMap = new HashMap<>();

    public void registry(Query.Type type, QueryHandler queryHandler) {
        handlerMap.put(type, queryHandler);
    }

    public QueryHandler get(Query.Type type) {
        return handlerMap.get(type);
    }

    public static QueryHandlerRegistry newInstance() {
        QueryHandlerRegistry registry = new QueryHandlerRegistry();
        ServiceLoader<QueryHandler> loader = ServiceLoader.load(QueryHandler.class);
        loader.forEach(o -> o.register(registry));
        return registry;
    }

}
