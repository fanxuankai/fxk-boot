package com.fanxuankai.boot.distributed.lock;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分布式锁生成工具
 *
 * @author fanxuankai
 */
public class LockKeyMaker {
    /**
     * key 前缀
     */
    public static final String DEFAULT_LOCK_KEY_PREFIX = "lock";

    /**
     * 生成 key
     *
     * @param prefix    前缀, 传空时取默认前缀
     * @param business  业务名
     * @param resources 资源, 可选
     * @return the key
     */
    public static String makeKey(String prefix, String business, List<Object> resources) {
        if (StrUtil.isBlank(prefix)) {
            prefix = DEFAULT_LOCK_KEY_PREFIX;
        }
        String key = prefix + StrPool.COLON + business;
        if (!CollectionUtil.isEmpty(resources)) {
            key += StrPool.COLON + resources.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(StrPool.COLON));
        }
        return key;
    }
}
