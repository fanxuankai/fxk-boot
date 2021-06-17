### 简介
分布式锁, 目前仅支持 Redis 分布式锁

### Getting started
- 添加依赖
```xml
<dependency>
    <groupId>com.fanxuankai.boot</groupId>
    <artifactId>fxk-boot-starter-distributed-lock</artifactId>
    <version>${latestVersion}</version>
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