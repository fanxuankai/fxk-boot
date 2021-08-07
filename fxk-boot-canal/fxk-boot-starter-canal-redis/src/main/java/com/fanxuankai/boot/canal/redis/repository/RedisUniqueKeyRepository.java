package com.fanxuankai.boot.canal.redis.repository;

import com.fanxuankai.boot.canal.redis.model.UniqueKey;
import com.fanxuankai.boot.canal.redis.model.UniqueKeyPro;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * Redis 唯一键查询 Repository
 *
 * @author fanxuankai
 */
public interface RedisUniqueKeyRepository<T> {

    /**
     * 查询
     *
     * @param uniqueKey UniqueKey
     * @return 有可能为 empty
     */
    Optional<T> findOne(@NonNull UniqueKey uniqueKey);

    /**
     * 判断是否存在
     *
     * @param uniqueKey UniqueKey
     * @return true or false
     */
    boolean exists(@NonNull UniqueKey uniqueKey);

    /**
     * 查询所有
     *
     * @param uniqueKeyPro UniqueKeyPro
     * @return 有可能为 empty
     */
    List<T> findAll(@NonNull UniqueKeyPro uniqueKeyPro);

    /**
     * 查询
     *
     * @param uniqueKey UniqueKey
     * @return 无记录抛出 NullPointerException
     */
    T getOne(@NonNull UniqueKey uniqueKey);

    /**
     * 查询所有
     *
     * @param uniqueKey uniqueKey name
     * @return 有可能为 empty
     */
    List<T> findAll(@Nullable String uniqueKey);
}
