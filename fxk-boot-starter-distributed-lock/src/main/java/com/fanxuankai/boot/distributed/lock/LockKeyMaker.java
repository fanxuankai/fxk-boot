package com.fanxuankai.boot.distributed.lock;

import jodd.util.StringPool;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
        if (prefix == null || prefix.length() == 0) {
            prefix = DEFAULT_LOCK_KEY_PREFIX;
        }
        String suffix = Optional.ofNullable(resources)
                .orElse(Collections.emptyList())
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(StringPool.COLON, StringPool.COLON, StringPool.EMPTY));
        return prefix + StringPool.COLON + business + suffix;
    }
}
