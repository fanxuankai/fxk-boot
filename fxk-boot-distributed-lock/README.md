### 简介
分布式锁

### Getting started
- 添加依赖
```xml
<dependency>
    <groupId>com.fanxuankai.boot</groupId>
    <artifactId>fxk-boot-starter-redis-lock</artifactId>
    <version>2.2.7-SNAPSHOT</version>
</dependency>
```
- Usage
```
// 代码加锁
@Resource
private DistributedLocker distributedLocker;

distributedLocker.lock("key", () -> System.out.println("do in lock"));

// 方法加锁：方法上加注解 @Lock
```