package com.fanxuankai.boot.redis;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author fanxuankai
 */
@Component
@SuppressWarnings({"unchecked", "unused"})
public class RedisUtils {
    private static DefaultTypingRedisTemplate template;
    private static ValueOperations<String, Object> valueOps;
    private static ListOperations<String, Object> listOps;
    private static HashOperations<String, String, Object> hashOps;
    private static ZSetOperations<String, Object> zSetOps;
    private static SetOperations<String, Object> setOps;

    public RedisUtils(DefaultTypingRedisTemplate defaultTypingRedisTemplate) {
        RedisUtils.template = defaultTypingRedisTemplate;
        valueOps = defaultTypingRedisTemplate.opsForValue();
        listOps = defaultTypingRedisTemplate.opsForList();
        hashOps = defaultTypingRedisTemplate.opsForHash();
        zSetOps = defaultTypingRedisTemplate.opsForZSet();
        setOps = defaultTypingRedisTemplate.opsForSet();
    }

    /* ------------------- key 相关操作--------------------- */

    public static class KeyOps {
        /**
         * 删除key
         *
         * @param key /
         */
        public static void delete(String key) {
            template.delete(key);
        }

        /**
         * 批量删除key
         *
         * @param keys /
         */
        public static void delete(Collection<String> keys) {
            template.delete(keys);
        }

        /**
         * 序列化key
         *
         * @param key /
         * @return /
         */
        public static byte[] dump(String key) {
            return template.dump(key);
        }

        /**
         * 是否存在key
         *
         * @param key /
         * @return /
         */
        public static Boolean hasKey(String key) {
            return template.hasKey(key);
        }

        /**
         * 设置过期时间
         *
         * @param key     /
         * @param timeout /
         * @param unit    /
         * @return /
         */
        public static Boolean expire(String key, long timeout, TimeUnit unit) {
            return template.expire(key, timeout, unit);
        }

        /**
         * 设置过期时间
         *
         * @param key  /
         * @param date /
         * @return /
         */
        public static Boolean expireAt(String key, Date date) {
            return template.expireAt(key, date);
        }

        /**
         * 查找匹配的key
         *
         * @param pattern /
         * @return /
         */
        public static Set<String> keys(String pattern) {
            return template.keys(pattern);
        }

        /**
         * 将当前数据库的 key 移动到给定的数据库 db 当中
         *
         * @param key     /
         * @param dbIndex /
         * @return /
         */
        public static Boolean move(String key, int dbIndex) {
            return template.move(key, dbIndex);
        }

        /**
         * 移除 key 的过期时间，key 将持久保持
         *
         * @param key /
         * @return /
         */
        public static Boolean persist(String key) {
            return template.persist(key);
        }

        /**
         * 返回 key 的剩余的过期时间
         *
         * @param key  /
         * @param unit /
         * @return /
         */
        public static Long getExpire(String key, TimeUnit unit) {
            return template.getExpire(key, unit);
        }

        /**
         * 返回 key 的剩余的过期时间
         *
         * @param key /
         * @return /
         */
        public static Long getExpire(String key) {
            return template.getExpire(key);
        }

        /**
         * 从当前数据库中随机返回一个 key
         *
         * @return /
         */
        public static String randomKey() {
            return template.randomKey();
        }

        /**
         * 修改 key 的名称
         *
         * @param oldKey /
         * @param newKey /
         */
        public static void rename(String oldKey, String newKey) {
            template.rename(oldKey, newKey);
        }

        /**
         * 仅当 newkey 不存在时，将 oldKey 改名为 newkey
         *
         * @param oldKey /
         * @param newKey /
         * @return /
         */
        public static Boolean renameIfAbsent(String oldKey, String newKey) {
            return template.renameIfAbsent(oldKey, newKey);
        }

        /**
         * 返回 key 所储存的值的类型
         *
         * @param key /
         * @return /
         */
        public static DataType type(String key) {
            return template.type(key);
        }
    }

    /* ------------------- value 相关操作--------------------- */

    public static class ValueOps {
        /**
         * 设置指定 key 的值
         *
         * @param key   /
         * @param value /
         * @param <T>   /
         */
        public static <T> void set(String key, T value) {
            valueOps.set(key, value);
        }

        /**
         * 获取指定 key 的值
         *
         * @param key /
         * @param <T> /
         * @return /
         */
        public static <T> T get(String key) {
            return (T) valueOps.get(key);
        }

        /**
         * 返回 key 中字符串值的子字符
         *
         * @param key   /
         * @param start /
         * @param end   /
         * @return /
         */
        public static String get(String key, long start, long end) {
            return valueOps.get(key, start, end);
        }

        /**
         * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
         *
         * @param key   /
         * @param value /
         * @param <T>   /
         * @return /
         */
        public static <T> T getAndSet(String key, T value) {
            return (T) valueOps.getAndSet(key, value);
        }

        /**
         * 对 key 所储存的字符串值，获取指定偏移量上的位(bit)
         *
         * @param key    /
         * @param offset /
         * @return /
         */
        public static Boolean getBit(String key, long offset) {
            return valueOps.getBit(key, offset);
        }

        /**
         * 批量获取
         *
         * @param keys /
         * @param <T>  /
         * @return /
         */
        public static <T> List<T> multiGet(Collection<String> keys) {
            return (List<T>) valueOps.multiGet(keys);
        }

        /**
         * 设置ASCII码, 字符串'a'的ASCII码是97, 转为二进制是'01100001', 此方法是将二进制第offset位值变为value
         *
         * @param key    /
         * @param offset 位置
         * @param value  值,true为1, false为0
         * @return /
         */
        public static Boolean setBit(String key, long offset, boolean value) {
            return valueOps.setBit(key, offset, value);
        }

        /**
         * 将值 value 关联到 key ，并将 key 的过期时间设为 timeout
         *
         * @param key     /
         * @param value   /
         * @param timeout 过期时间
         * @param unit    时间单位, 天:TimeUnit.DAYS 小时:TimeUnit.HOURS 分钟:TimeUnit.MINUTES
         *                秒:TimeUnit.SECONDS 毫秒:TimeUnit.MILLISECONDS
         * @param <T>     /
         */
        public static <T> void set(String key, T value, long timeout, TimeUnit unit) {
            valueOps.set(key, value, timeout, unit);
        }

        /**
         * 只有在 key 不存在时设置 key 的值
         *
         * @param key   /
         * @param value /
         * @param <T>   /
         * @return / 之前已经存在返回false, 不存在返回true
         */
        public static <T> Boolean setIfAbsent(String key, T value) {
            return valueOps.setIfAbsent(key, value);
        }

        /**
         * 只有在 key 不存在时设置 key 的值
         *
         * @param key     /
         * @param value   /
         * @param timeout /
         * @param <T>     /
         * @return / 之前已经存在返回false, 不存在返回true
         */
        public static <T> Boolean setIfAbsent(String key, T value, Duration timeout) {
            return valueOps.setIfAbsent(key, value, timeout);
        }

        /**
         * 只有在 key 不存在时设置 key 的值
         *
         * @param key     /
         * @param value   /
         * @param timeout /
         * @param unit    /
         * @param <T>     /
         * @return / 之前已经存在返回false, 不存在返回true
         */
        public static <T> Boolean setIfAbsent(String key, T value, long timeout, TimeUnit unit) {
            return valueOps.setIfAbsent(key, value, timeout, unit);
        }

        /**
         * 用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始
         *
         * @param key    /
         * @param value  /
         * @param offset 从指定位置开始覆写
         * @param <T>    /
         */
        public static <T> void set(String key, T value, long offset) {
            valueOps.set(key, value, offset);
        }

        /**
         * 获取字符串的长度
         *
         * @param key /
         * @return /
         */
        public static Long size(String key) {
            return valueOps.size(key);
        }

        /**
         * 批量添加
         *
         * @param maps /
         * @param <T>  /
         */
        public static <T> void multiSet(Map<String, T> maps) {
            valueOps.multiSet(maps);
        }

        /**
         * 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在
         *
         * @param maps /
         * @param <T>  /
         * @return / 之前已经存在返回false, 不存在返回true
         */
        public static <T> Boolean multiSetIfAbsent(Map<String, T> maps) {
            return valueOps.multiSetIfAbsent(maps);
        }

        /**
         * 增加(自增长), 负数则为自减
         *
         * @param key       /
         * @param increment /
         * @return /
         */
        public static Long increment(String key, long increment) {
            return valueOps.increment(key, increment);
        }

        /**
         * @param key       /
         * @param increment /
         * @return /
         */
        public static Double increment(String key, double increment) {
            return valueOps.increment(key, increment);
        }

        /**
         * 追加到末尾
         *
         * @param key   /
         * @param value /
         * @return /
         */
        public static Integer append(String key, String value) {
            return valueOps.append(key, value);
        }
    }

    /* ------------------- hash 相关操作------------------------- */

    public static class HashOps {
        /**
         * 获取存储在哈希表中指定字段的值
         *
         * @param key   /
         * @param field /
         * @param <T>   /
         * @return /
         */
        public static <T> T get(String key, String field) {
            HashOperations<String, String, T> hashOps = template.opsForHash();
            return hashOps.get(key, field);
        }

        /**
         * 获取所有给定字段的值
         *
         * @param key /
         * @param <T> /
         * @return /
         */
        public static <T> Map<String, T> entries(String key) {
            HashOperations<String, String, T> hashOps = template.opsForHash();
            return hashOps.entries(key);
        }

        /**
         * 获取所有给定字段的值
         *
         * @param key    /
         * @param fields /
         * @param <T>    /
         * @return /
         */
        public static <T> List<T> multiGet(String key, Collection<String> fields) {
            HashOperations<String, String, T> hashOps = template.opsForHash();
            return hashOps.multiGet(key, fields);
        }

        /**
         * hash.put
         *
         * @param key     /
         * @param hashKey /
         * @param value   /
         * @param <T>     /
         */
        public static <T> void put(String key, String hashKey, T value) {
            hashOps.put(key, hashKey, value);
        }

        /**
         * hash.putAll
         *
         * @param key  /
         * @param maps /
         * @param <T>  /
         */
        public static <T> void putAll(String key, Map<String, T> maps) {
            hashOps.putAll(key, maps);
        }

        /**
         * 仅当hashKey不存在时才设置
         *
         * @param key     /
         * @param hashKey /
         * @param value   /
         * @param <T>     /
         * @return /
         */
        public static <T> Boolean putIfAbsent(String key, String hashKey, T value) {
            return hashOps.putIfAbsent(key, hashKey, value);
        }

        /**
         * 删除一个或多个哈希表字段
         *
         * @param key    /
         * @param fields /
         * @return /
         */
        public static Long delete(String key, String... fields) {
            return hashOps.delete(key, Arrays.stream(fields).toArray(Object[]::new));
        }

        /**
         * 查看哈希表 key 中，指定的字段是否存在
         *
         * @param key   /
         * @param field /
         * @return /
         */
        public static Boolean hasKey(String key, String field) {
            return hashOps.hasKey(key, field);
        }

        /**
         * 为哈希表 key 中的指定字段的整数值加上增量 increment
         *
         * @param key       /
         * @param field     /
         * @param increment /
         * @return /
         */
        public static Long increment(String key, String field, long increment) {
            return hashOps.increment(key, field, increment);
        }

        /**
         * 为哈希表 key 中的指定字段的整数值加上增量 increment
         *
         * @param key   /
         * @param field /
         * @param delta /
         * @return /
         */
        public static Double increment(String key, String field, double delta) {
            return hashOps.increment(key, field, delta);
        }

        /**
         * 获取所有哈希表中的字段
         *
         * @param key /
         * @return /
         */
        public static Set<String> keys(String key) {
            return hashOps.keys(key);
        }

        /**
         * 获取哈希表中字段的数量
         *
         * @param key /
         * @return /
         */
        public static Long size(String key) {
            return hashOps.size(key);
        }

        /**
         * 获取哈希表中所有值
         *
         * @param key /
         * @param <T> /
         * @return /
         */
        public static <T> List<T> values(String key) {
            HashOperations<String, String, T> hashOps = template.opsForHash();
            return hashOps.values(key);
        }

        /**
         * 迭代哈希表中的键值对
         *
         * @param key     /
         * @param options /
         * @param <T>     /
         * @return /
         */
        public static <T> Cursor<Map.Entry<String, T>> scan(String key, ScanOptions options) {
            HashOperations<String, String, T> hashOps = template.opsForHash();
            return hashOps.scan(key, options);
        }
    }

    /* ------------------------ list 相关操作---------------------------- */

    public static class ListOps {
        /**
         * 通过索引获取列表中的元素
         *
         * @param key   /
         * @param index /
         * @param <T>   /
         * @return /
         */
        public static <T> T index(String key, long index) {
            return (T) listOps.index(key, index);
        }

        /**
         * 获取列表指定范围内的元素
         *
         * @param key   /
         * @param start 开始位置, 0是开始位置
         * @param end   结束位置, -1返回所有
         * @param <T>   /
         * @return /
         */
        public static <T> List<T> range(String key, long start, long end) {
            return (List<T>) listOps.range(key, start, end);
        }

        /**
         * 存储在list头部
         *
         * @param key   /
         * @param value /
         * @param <T>   /
         * @return /
         */
        public static <T> Long leftPush(String key, T value) {
            return listOps.leftPush(key, value);
        }

        /**
         * @param key    /
         * @param values /
         * @param <T>    /
         * @return /
         */
        public static <T> Long leftPushAll(String key, T... values) {
            return listOps.leftPushAll(key, values);
        }

        /**
         * @param key    /
         * @param values /
         * @param <T>    /
         * @return /
         */
        public static <T> Long leftPushAll(String key, Collection<T> values) {
            return listOps.leftPushAll(key, values.toArray());
        }

        /**
         * 当list存在的时候才加入
         *
         * @param key   /
         * @param value /
         * @param <T>   /
         * @return /
         */
        public static <T> Long leftPushIfPresent(String key, T value) {
            return listOps.leftPushIfPresent(key, value);
        }

        /**
         * 如果pivot存在,再pivot前面添加
         *
         * @param key   /
         * @param pivot /
         * @param value /
         * @param <T>   /
         * @return /
         */
        public static <T> Long leftPush(String key, String pivot, T value) {
            return listOps.leftPush(key, pivot, value);
        }

        /**
         * @param key   /
         * @param value /
         * @param <T>   /
         * @return /
         */
        public static <T> Long rightPush(String key, T value) {
            return listOps.rightPush(key, value);
        }

        /**
         * @param key   /
         * @param value /
         * @param <T>   /
         * @return /
         */
        public static <T> Long rightPushAll(String key, T... value) {
            return listOps.rightPushAll(key, value);
        }

        /**
         * @param key   /
         * @param value /
         * @param <T>   /
         * @return /
         */
        public static <T> Long rightPushAll(String key, Collection<T> value) {
            return listOps.rightPushAll(key, value.toArray());
        }

        /**
         * 为已存在的列表添加值
         *
         * @param key   /
         * @param value /
         * @param <T>   /
         * @return /
         */
        public static <T> Long rightPushIfPresent(String key, T value) {
            return listOps.rightPushIfPresent(key, value);
        }

        /**
         * 在pivot元素的右边添加值
         *
         * @param key   /
         * @param pivot /
         * @param value /
         * @param <T>   /
         * @return /
         */
        public static <T> Long rightPush(String key, String pivot, T value) {
            return listOps.rightPush(key, pivot, value);
        }

        /**
         * 通过索引设置列表元素的值
         *
         * @param key   /
         * @param index 位置
         * @param value /
         * @param <T>   /
         */
        public static <T> void set(String key, long index, T value) {
            listOps.set(key, index, value);
        }

        /**
         * 移出并获取列表的第一个元素
         *
         * @param key /
         * @param <T> /
         * @return / 删除的元素
         */
        public static <T> T leftPop(String key) {
            return (T) listOps.leftPop(key);
        }

        /**
         * 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
         *
         * @param key     /
         * @param timeout 等待时间
         * @param unit    时间单位
         * @param <T>     /
         * @return /
         */
        public static <T> T leftPop(String key, long timeout, TimeUnit unit) {
            return (T) listOps.leftPop(key, timeout, unit);
        }

        /**
         * 移除并获取列表最后一个元素
         *
         * @param key /
         * @param <T> /
         * @return / 删除的元素
         */
        public static <T> T rightPop(String key) {
            return (T) listOps.rightPop(key);
        }

        /**
         * 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
         *
         * @param key     /
         * @param timeout 等待时间
         * @param unit    时间单位
         * @param <T>     /
         * @return /
         */
        public static <T> T rightPop(String key, long timeout, TimeUnit unit) {
            return (T) listOps.rightPop(key, timeout, unit);
        }

        /**
         * 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
         *
         * @param sourceKey      /
         * @param destinationKey /
         * @param <T>            /
         * @return /
         */
        public static <T> T rightPopAndLeftPush(String sourceKey, String destinationKey) {
            return (T) listOps.rightPopAndLeftPush(sourceKey, destinationKey);
        }

        /**
         * 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
         *
         * @param sourceKey      /
         * @param destinationKey /
         * @param timeout        /
         * @param unit           /
         * @param <T>            /
         * @return /
         */
        public static <T> T rightPopAndLeftPush(String sourceKey, String destinationKey, long timeout, TimeUnit unit) {
            return (T) listOps.rightPopAndLeftPush(sourceKey, destinationKey, timeout, unit);
        }

        /**
         * 删除集合中值等于value得元素
         *
         * @param key   /
         * @param index index 为 0, 删除所有值等于value的元素;
         *              index 大于 0, 从头部开始删除第一个值等于value的元素;
         *              index 小于 0, 从尾部开始删除第一个值等于value的元素;
         * @param value /
         * @param <T>   /
         * @return /
         */
        public static <T> Long remove(String key, long index, T value) {
            return listOps.remove(key, index, value);
        }

        /**
         * 裁剪list
         *
         * @param key   /
         * @param start /
         * @param end   /
         */
        public static void trim(String key, long start, long end) {
            listOps.trim(key, start, end);
        }

        /**
         * 获取列表长度
         *
         * @param key /
         * @return /
         */
        public static Long size(String key) {
            return listOps.size(key);
        }
    }

    /* -------------------- set 相关操作-------------------------- */

    public static class SetOps {
        /**
         * set添加元素
         *
         * @param key    /
         * @param values /
         * @param <T>    /
         * @return /
         */
        public static <T> Long add(String key, T... values) {
            return setOps.add(key, values);
        }

        /**
         * set添加元素
         *
         * @param key    /
         * @param values /
         * @param <T>    /
         * @return /
         */
        public static <T> Long add(String key, Collection<T> values) {
            return setOps.add(key, values.toArray());
        }

        /**
         * set移除元素
         *
         * @param key    /
         * @param values /
         * @param <T>    /
         * @return /
         */
        public static <T> Long remove(String key, T... values) {
            return setOps.remove(key, Arrays.stream(values).toArray());
        }

        /**
         * 移除并返回集合的一个随机元素
         *
         * @param key /
         * @param <T> /
         * @return /
         */
        public static <T> T pop(String key) {
            return (T) setOps.pop(key);
        }

        /**
         * 将元素value从一个集合移到另一个集合
         *
         * @param key     /
         * @param value   /
         * @param destKey /
         * @param <T>     /
         * @return /
         */
        public static <T> Boolean move(String key, T value, String destKey) {
            return setOps.move(key, value, destKey);
        }

        /**
         * 获取集合的大小
         *
         * @param key /
         * @return /
         */
        public static Long size(String key) {
            return setOps.size(key);
        }

        /**
         * 判断集合是否包含value
         *
         * @param key   /
         * @param value /
         * @param <T>   /
         * @return /
         */
        public static <T> Boolean isMember(String key, T value) {
            return setOps.isMember(key, value);
        }

        /**
         * 获取两个集合的交集
         *
         * @param key      /
         * @param otherKey /
         * @param <T>      /
         * @return /
         */
        public static <T> Set<T> intersect(String key, String otherKey) {
            return (Set<T>) setOps.intersect(key, otherKey);
        }

        /**
         * 获取key集合与多个集合的交集
         *
         * @param key       /
         * @param otherKeys /
         * @param <T>       /
         * @return /
         */
        public static <T> Set<T> intersect(String key, Collection<String> otherKeys) {
            return (Set<T>) setOps.intersect(key, otherKeys);
        }

        /**
         * key集合与otherKey集合的交集存储到destKey集合中
         *
         * @param key      /
         * @param otherKey /
         * @param destKey  /
         * @return /
         */
        public static Long intersectAndStore(String key, String otherKey, String destKey) {
            return setOps.intersectAndStore(key, otherKey, destKey);
        }

        /**
         * key集合与多个集合的交集存储到destKey集合中
         *
         * @param key       /
         * @param otherKeys /
         * @param destKey   /
         * @return /
         */
        public static Long intersectAndStore(String key, Collection<String> otherKeys, String destKey) {
            return setOps.intersectAndStore(key, otherKeys, destKey);
        }

        /**
         * 获取两个集合的并集
         *
         * @param key       /
         * @param otherKeys /
         * @param <T>       /
         * @return /
         */
        public static <T> Set<T> union(String key, String otherKeys) {
            return (Set<T>) setOps.union(key, otherKeys);
        }

        /**
         * 获取key集合与多个集合的并集
         *
         * @param key       /
         * @param otherKeys /
         * @param <T>       /
         * @return /
         */
        public static <T> Set<T> union(String key, Collection<String> otherKeys) {
            return (Set<T>) setOps.union(key, otherKeys);
        }

        /**
         * key集合与otherKey集合的并集存储到destKey中
         *
         * @param key      /
         * @param otherKey /
         * @param destKey  /
         * @return /
         */
        public static Long unionAndStore(String key, String otherKey, String destKey) {
            return setOps.unionAndStore(key, otherKey, destKey);
        }

        /**
         * key集合与多个集合的并集存储到destKey中
         *
         * @param key       /
         * @param otherKeys /
         * @param destKey   /
         * @return /
         */
        public static Long unionAndStore(String key, Collection<String> otherKeys, String destKey) {
            return setOps.unionAndStore(key, otherKeys, destKey);
        }

        /**
         * 获取两个集合的差集
         *
         * @param key      /
         * @param otherKey /
         * @param <T>      /
         * @return /
         */
        public static <T> Set<T> difference(String key, String otherKey) {
            return (Set<T>) setOps.difference(key, otherKey);
        }

        /**
         * 获取key集合与多个集合的差集
         *
         * @param key       /
         * @param otherKeys /
         * @param <T>       /
         * @return /
         */
        public static <T> Set<T> difference(String key, Collection<String> otherKeys) {
            return (Set<T>) setOps.difference(key, otherKeys);
        }

        /**
         * key集合与otherKey集合的差集存储到destKey中
         *
         * @param key      /
         * @param otherKey /
         * @param destKey  /
         * @return /
         */
        public static Long differenceAndStore(String key, String otherKey, String destKey) {
            return setOps.differenceAndStore(key, otherKey, destKey);
        }

        /**
         * key集合与多个集合的差集存储到destKey中
         *
         * @param key       /
         * @param otherKeys /
         * @param destKey   /
         * @return /
         */
        public static Long differenceAndStore(String key, Collection<String> otherKeys,
                                              String destKey) {
            return setOps.differenceAndStore(key, otherKeys, destKey);
        }

        /**
         * 获取集合所有元素
         *
         * @param key /
         * @param <T> /
         * @return /
         */
        public static <T> Set<T> members(String key) {
            return (Set<T>) setOps.members(key);
        }

        /**
         * 随机获取集合中的一个元素
         *
         * @param key /
         * @param <T> /
         * @return /
         */
        public static <T> T randomMember(String key) {
            return (T) setOps.randomMember(key);
        }

        /**
         * 随机获取集合中count个元素
         *
         * @param key   /
         * @param count /
         * @param <T>   /
         * @return /
         */
        public static <T> List<T> randomMembers(String key, long count) {
            return (List<T>) setOps.randomMembers(key, count);
        }

        /**
         * 随机获取集合中count个元素并且去除重复的
         *
         * @param key   /
         * @param count /
         * @param <T>   /
         * @return /
         */
        public static <T> Set<T> distinctRandomMembers(String key, long count) {
            return (Set<T>) setOps.distinctRandomMembers(key, count);
        }

        /**
         * @param key     /
         * @param options /
         * @param <T>     /
         * @return /
         */
        public static <T> Cursor<T> scan(String key, ScanOptions options) {
            return (Cursor<T>) setOps.scan(key, options);
        }
    }

    /*------------------ zSet 相关操作--------------------------------*/

    public static class ZSetOps {
        /**
         * 添加元素,有序集合是按照元素的score值由小到大排列
         *
         * @param key   /
         * @param value /
         * @param score /
         * @param <T>   /
         * @return /
         */
        public static <T> Boolean add(String key, T value, double score) {
            return zSetOps.add(key, value, score);
        }

        /**
         * @param key    /
         * @param values /
         * @param <T>    /
         * @return /
         */
        public static <T> Long add(String key, Set<ZSetOperations.TypedTuple<T>> values) {
            ZSetOperations<String, T> ops = (ZSetOperations<String, T>) zSetOps;
            return ops.add(key, values);
        }

        /**
         * @param key    /
         * @param values /
         * @param <T>    /
         * @return /
         */
        public static <T> Long remove(String key, T... values) {
            return zSetOps.remove(key, values);
        }

        /**
         * 增加元素的score值，并返回增加后的值
         *
         * @param key   /
         * @param value /
         * @param delta /
         * @param <T>   /
         * @return /
         */
        public static <T> Double incrementScore(String key, T value, double delta) {
            return zSetOps.incrementScore(key, value, delta);
        }

        /**
         * 返回元素在集合的排名,有序集合是按照元素的score值由小到大排列
         *
         * @param key   /
         * @param value /
         * @param <T>   /
         * @return / 0表示第一位
         */
        public static <T> Long rank(String key, T value) {
            return zSetOps.rank(key, value);
        }

        /**
         * 返回元素在集合的排名,按元素的score值由大到小排列
         *
         * @param key   /
         * @param value /
         * @param <T>   /
         * @return /
         */
        public static <T> Long reverseRank(String key, T value) {
            return zSetOps.reverseRank(key, value);
        }

        /**
         * 获取集合的元素, 从小到大排序
         *
         * @param key   /
         * @param start 开始位置
         * @param end   结束位置, -1查询所有
         * @param <T>   /
         * @return /
         */
        public static <T> Set<T> range(String key, long start, long end) {
            return (Set<T>) zSetOps.range(key, start, end);
        }

        /**
         * 获取集合元素, 并且把score值也获取
         *
         * @param key   /
         * @param start /
         * @param end   /
         * @param <T>   /
         * @return /
         */
        public static <T> Set<ZSetOperations.TypedTuple<T>> rangeWithScores(String key, long start,
                                                                            long end) {
            ZSetOperations<String, T> ops = (ZSetOperations<String, T>) zSetOps;
            return ops.rangeWithScores(key, start, end);
        }

        /**
         * 根据Score值查询集合元素
         *
         * @param key /
         * @param min 最小值
         * @param max 最大值
         * @param <T> /
         * @return /
         */
        public static <T> Set<T> rangeByScore(String key, double min, double max) {
            return (Set<T>) zSetOps.rangeByScore(key, min, max);
        }

        /**
         * 根据Score值查询集合元素, 从小到大排序
         *
         * @param key /
         * @param min 最小值
         * @param max 最大值
         * @param <T> /
         * @return /
         */
        public static <T> Set<ZSetOperations.TypedTuple<T>> rangeByScoreWithScores(String key, double min, double max) {
            ZSetOperations<String, T> ops = (ZSetOperations<String, T>) zSetOps;
            return ops.rangeByScoreWithScores(key, min, max);
        }

        /**
         * @param key   /
         * @param min   /
         * @param max   /
         * @param start /
         * @param end   /
         * @param <T>   /
         * @return /
         */
        public static <T> Set<ZSetOperations.TypedTuple<T>> rangeByScoreWithScores(String key, double min, double max,
                                                                                   long start, long end) {
            ZSetOperations<String, T> ops = (ZSetOperations<String, T>) zSetOps;
            return ops.rangeByScoreWithScores(key, min, max, start, end);
        }

        /**
         * 获取集合的元素, 从大到小排序
         *
         * @param key   /
         * @param start /
         * @param end   /
         * @param <T>   /
         * @return /
         */
        public static <T> Set<T> reverseRange(String key, long start, long end) {
            return (Set<T>) zSetOps.reverseRange(key, start, end);
        }

        /**
         * 获取集合的元素, 从大到小排序, 并返回score值
         *
         * @param key   /
         * @param start /
         * @param end   /
         * @param <T>   /
         * @return /
         */
        public static <T> Set<ZSetOperations.TypedTuple<T>> reverseRangeWithScores(String key, long start, long end) {
            ZSetOperations<String, T> ops = (ZSetOperations<String, T>) zSetOps;
            return ops.reverseRangeWithScores(key, start, end);
        }

        /**
         * 根据Score值查询集合元素, 从大到小排序
         *
         * @param key /
         * @param min /
         * @param max /
         * @param <T> /
         * @return /
         */
        public static <T> Set<T> reverseRangeByScore(String key, double min, double max) {
            return (Set<T>) zSetOps.reverseRangeByScore(key, min, max);
        }

        /**
         * 根据Score值查询集合元素, 从大到小排序
         *
         * @param key /
         * @param min /
         * @param max /
         * @param <T> /
         * @return /
         */
        public static <T> Set<ZSetOperations.TypedTuple<T>> reverseRangeByScoreWithScores(String key, double min,
                                                                                          double max) {
            ZSetOperations<String, T> ops = (ZSetOperations<String, T>) zSetOps;
            return ops.reverseRangeByScoreWithScores(key, min, max);
        }

        /**
         * @param key   /
         * @param min   /
         * @param max   /
         * @param start /
         * @param end   /
         * @param <T>   /
         * @return /
         */
        public static <T> Set<T> reverseRangeByScore(String key, double min, double max, long start, long end) {
            return (Set<T>) zSetOps.reverseRangeByScore(key, min, max, start, end);
        }

        /**
         * 根据score值获取集合元素数量
         *
         * @param key /
         * @param min /
         * @param max /
         * @return /
         */
        public static Long count(String key, double min, double max) {
            return zSetOps.count(key, min, max);
        }

        /**
         * 获取集合大小
         *
         * @param key /
         * @return /
         */
        public static Long size(String key) {
            return zSetOps.size(key);
        }

        /**
         * 获取集合大小
         *
         * @param key /
         * @return /
         */
        public static Long zCard(String key) {
            return zSetOps.zCard(key);
        }

        /**
         * 获取集合中value元素的score值
         *
         * @param key   /
         * @param value /
         * @param <T>   /
         * @return /
         */
        public static <T> Double score(String key, T value) {
            return zSetOps.score(key, value);
        }

        /**
         * 移除指定索引位置的成员
         *
         * @param key   /
         * @param start /
         * @param end   /
         * @return /
         */
        public static Long removeRange(String key, long start, long end) {
            return zSetOps.removeRange(key, start, end);
        }

        /**
         * 根据指定的score值的范围来移除成员
         *
         * @param key /
         * @param min /
         * @param max /
         * @return /
         */
        public static Long removeRangeByScore(String key, double min, double max) {
            return zSetOps.removeRangeByScore(key, min, max);
        }

        /**
         * 获取key和otherKey的并集并存储在destKey中
         *
         * @param key      /
         * @param otherKey /
         * @param destKey  /
         * @return /
         */
        public static Long unionAndStore(String key, String otherKey, String destKey) {
            return zSetOps.unionAndStore(key, otherKey, destKey);
        }

        /**
         * @param key       /
         * @param otherKeys /
         * @param destKey   /
         * @return /
         */
        public static Long unionAndStore(String key, Collection<String> otherKeys, String destKey) {
            return zSetOps.unionAndStore(key, otherKeys, destKey);
        }

        /**
         * 交集
         *
         * @param key      /
         * @param otherKey /
         * @param destKey  /
         * @return /
         */
        public static Long intersectAndStore(String key, String otherKey, String destKey) {
            return zSetOps.intersectAndStore(key, otherKey, destKey);
        }

        /**
         * 交集
         *
         * @param key       /
         * @param otherKeys /
         * @param destKey   /
         * @return /
         */
        public static Long intersectAndStore(String key, Collection<String> otherKeys, String destKey) {
            return zSetOps.intersectAndStore(key, otherKeys, destKey);
        }

        /**
         * @param key     /
         * @param options /
         * @param <T>     /
         * @return /
         */
        public static <T> Cursor<ZSetOperations.TypedTuple<T>> scan(String key, ScanOptions options) {
            ZSetOperations<String, T> ops = (ZSetOperations<String, T>) zSetOps;
            return ops.scan(key, options);
        }
    }
}
