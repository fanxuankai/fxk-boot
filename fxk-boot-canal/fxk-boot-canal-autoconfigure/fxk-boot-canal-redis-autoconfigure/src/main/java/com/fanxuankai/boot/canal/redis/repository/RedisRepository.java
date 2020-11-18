package com.fanxuankai.boot.canal.redis.repository;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * Redis Repository 接口
 *
 * @author fanxuankai
 */
public interface RedisRepository<T, ID> extends RedisUniqueKeyRepository<T>, RedisCombineKeyRepository<T> {
    /**
     * 指定id查询
     *
     * @param id 主键
     * @return 有可能为empty
     */
    Optional<T> findById(@Nullable ID id);

    /**
     * 指定id判断是否存在
     *
     * @param id 主键
     * @return true or false
     */
    boolean existsById(@Nullable ID id);

    /**
     * 查询所有
     *
     * @return list
     */
    List<T> findAll();

    /**
     * 指定id查询
     *
     * @param ids 主键
     * @return 有可能为empty
     */
    List<T> findAllById(@Nullable Iterable<ID> ids);

    /**
     * 判断数量
     *
     * @return long
     */
    long count();

    /**
     * 指定id查询
     *
     * @param id 主键
     * @return 无记录抛出 NullPointerException
     */
    T getOne(@NonNull ID id);
}
