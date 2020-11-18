package com.fanxuankai.boot.canal.redis.repository;

import com.alibaba.fastjson.JSON;
import com.fanxuankai.boot.canal.redis.annotation.CanalRedis;
import com.fanxuankai.boot.canal.redis.model.CombineKeyModel;
import com.fanxuankai.boot.canal.redis.model.Entry;
import com.fanxuankai.boot.canal.redis.model.UniqueKey;
import com.fanxuankai.boot.canal.redis.model.UniqueKeyPro;
import com.fanxuankai.canal.core.annotation.CanalTableCache;
import com.fanxuankai.canal.core.util.RedisKey;
import com.google.common.collect.Sets;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * RedisRepository 实现类
 *
 * @author fanxuankai
 */
public class SimpleRedisRepository<T, ID> implements RedisRepository<T, ID> {

    private final Optional<CanalRedis> canalRedisOptional;
    private final Class<T> domainType;
    private final RedisTemplate<Object, Object> redisTemplate;
    private final String schema;
    private final String tableName;

    /**
     * 构造器
     *
     * @param domainType    the domain type
     * @param redisTemplate the RedisTemplate
     */
    public SimpleRedisRepository(Class<T> domainType, RedisTemplate<Object, Object> redisTemplate) {
        this.domainType = domainType;
        this.redisTemplate = redisTemplate;
        this.canalRedisOptional = Optional.ofNullable(AnnotationUtils.findAnnotation(domainType, CanalRedis.class));
        this.schema = CanalTableCache.getSchema(domainType);
        this.tableName = CanalTableCache.getTableName(domainType);
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(id)
                .map(Object::toString)
                .flatMap(hashKey -> convert(redisTemplate.opsForHash().get(key(), hashKey)));
    }

    @Override
    public boolean existsById(ID id) {
        return findById(id).isPresent();
    }

    @Override
    public List<T> findAll() {
        return getAll(key());
    }

    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        return multiGet(key(), Optional.ofNullable(ids)
                .map(idsIt -> {
                    Set<Object> idSet = Sets.newHashSet();
                    for (Object id : idsIt) {
                        if (id == null) {
                            continue;
                        }
                        idSet.add(id.toString());
                    }
                    return idSet;
                })
                .orElse(Collections.emptySet()));
    }

    @Override
    public long count() {
        return redisTemplate.opsForHash().size(key());
    }

    @Override
    public T getOne(ID id) {
        return findById(id).orElseThrow(NullPointerException::new);
    }

    @Override
    public Optional<T> findOne(UniqueKey uniqueKey) {
        return convert(redisTemplate.opsForHash().get(keyWithSuffix(uniqueKey.getName()),
                uniqueKey.getValue().toString()));
    }

    @Override
    public boolean exists(UniqueKey uniqueKey) {
        return findOne(uniqueKey).isPresent();
    }

    @Override
    public Optional<T> findOne(CombineKeyModel combineKeyModel) {
        List<Entry> entries = combineKeyModel.getEntries();
        List<String> names = entries.stream().map(Entry::getName).collect(Collectors.toList());
        String suffix = RedisKey.suffix(names);
        String key = keyWithSuffix(suffix);
        String hashKey = RedisKey.hashKey(names, entries.stream()
                .collect(Collectors.toMap(Entry::getName, o -> o.getValue().toString())));
        return convert(redisTemplate.opsForHash().get(key, hashKey));
    }

    @Override
    public T getOne(CombineKeyModel combineKeyModel) {
        return findOne(combineKeyModel).orElseThrow(NullPointerException::new);
    }

    @Override
    public List<T> findAll(List<String> combineKey) {
        String suffix = RedisKey.suffix(combineKey);
        String key = keyWithSuffix(suffix);
        return getAll(key);
    }

    @Override
    public List<T> findAll(UniqueKeyPro uniqueKeyPro) {
        return multiGet(keyWithSuffix(uniqueKeyPro.getName()), uniqueKeyPro.getValues()
                .stream()
                .map(Objects::toString)
                .collect(Collectors.toSet()));
    }

    @Override
    public T getOne(UniqueKey uniqueKey) {
        return findOne(uniqueKey).orElseThrow(NullPointerException::new);
    }

    @Override
    public List<T> findAll(String uniqueKey) {
        return getAll(keyWithSuffix(uniqueKey));
    }

    private List<T> getAll(String key) {
        return Optional.ofNullable(key)
                .map(s -> convertList(redisTemplate.opsForHash().values(s)))
                .orElse(Collections.emptyList());
    }

    private List<T> multiGet(String key, Collection<Object> hashKeys) {
        if (key == null || CollectionUtils.isEmpty(hashKeys)) {
            return Collections.emptyList();
        }
        return convertList(redisTemplate.opsForHash().multiGet(key, hashKeys));
    }

    private List<T> convertList(List<Object> objects) {
        objects = objects.stream().filter(Objects::nonNull).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(objects)) {
            return Collections.emptyList();
        }
        return objects.stream()
                .map(o -> JSON.parseObject(JSON.toJSONString(o), domainType))
                .collect(Collectors.toList());
    }

    private Optional<T> convert(Object object) {
        return Optional.ofNullable(object).map(o -> JSON.parseObject(JSON.toJSONString(o), domainType));
    }

    private String key() {
        return canalRedisOptional.map(CanalRedis::key).orElse(RedisKey.of(schema, tableName));
    }

    private String keyWithSuffix(String suffix) {
        return canalRedisOptional.map(o -> RedisKey.withSuffix(o.key(), suffix))
                .orElse(RedisKey.of(schema, tableName, suffix));
    }

}