## Spring Boot Starter Redis
自动装配 RedisTemplate, key 和 hashKey 使用字符串序列化, value 和 hashValue 使用 JSON 序列化  
基于 RedisTemplate 封装 RedisUtils, 使 RedisTemplate 操作更简便  
装配 RedisTemplate 成功的同时自动装配 RedisUtils, 直接使用静态方法即可
## Getting Started
- 添加 maven 依赖
```xml
<dependency>
    <groupId>com.fanxuankai.boot</groupId>
    <artifactId>fxk-boot-starter-redis</artifactId>
    <version>${latestVersion}</version>
</dependency>
```
- RedisUtils 使用示例
```
        String key = "key";
        KeyOps.getExpire(key);
        KeyOps.getExpire(key, TimeUnit.SECONDS);

        String value = "value";
        ValueOps.get(value);
        ValueOps.getBit(value, 1);
        ValueOps.get(value, 1, 10);
        ValueOps.set(value, "fxk");
        ValueOps.multiSet(MapUtil.of(value, "f1234"));
        ValueOps.set(value, "xk", 1);

        String list = "list";
        ListOps.leftPush(list, 1);
        ListOps.rightPop(list);
        ListOps.rightPush(list, 2);
        ListOps.leftPop(list);
        ListOps.index(list, 0);
        ListOps.leftPushAll(list, 1, 2, 3);

        String set = "set";
        String[] ss = new String[1];
        SetOps.add(set, ss);
        SetOps.add(set, Arrays.asList(1, 2, 3));
        SetOps.isMember(set, 1);
        SetOps.union(set, set);

        String zSet = "zSet";
        ZSetOps.add(zSet, 1, 1.0);
        System.out.println(ZSetOps.rank(zSet, 1));
        System.out.println(ZSetOps.score(zSet, 1));

        String hash = "hash";
        HashOps.put(hash, "name", "范旋凯");
        String name = HashOps.get(hash, "name");
        System.out.println(name);
```