package com.fanxuankai.boot.canal.redis.repository;

import com.fanxuankai.boot.canal.redis.model.CombineKeyModel;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * Redis 组合键查询 Repository 接口
 *
 * @author fanxuankai
 */
public interface RedisCombineKeyRepository<T> {

    /**
     * 查询
     *
     * @param combineKeyModel 组合键值对
     * @return 查询该组合键下哈希表的某个 hashKey 无数据返回 empty
     */
    Optional<T> findOne(@NonNull CombineKeyModel combineKeyModel);

    /**
     * 查询
     *
     * @param combineKeyModel CombineKeyModel
     * @return 无记录抛出 NullPointerException
     */
    T getOne(@NonNull CombineKeyModel combineKeyModel);

    /**
     * 查询所有
     *
     * @param combineKey 组合键
     * @return 返回该组合键下整个哈希表
     */
    List<T> findAll(@Nullable List<String> combineKey);

}
