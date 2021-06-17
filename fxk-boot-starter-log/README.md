### 简介
日志工具, 自动记录系统日志  
实现原理: 自定义注解, 基于 Spring AOP 实现方法拦截, 支持 Logger、Jdbc 方式记录日志信息, 默认为 Logger 方式

### Getting started
- 建表(Jdbc 方式)
```sql
DROP TABLE
IF
	EXISTS sys_log;
CREATE TABLE `sys_log` (
	`id` BIGINT ( 20 ) NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`resource` VARCHAR ( 255 ) DEFAULT NULL COMMENT '资源名称',
	`uri` VARCHAR ( 255 ) DEFAULT NULL COMMENT '统一资源标识符',
	`safety_level` INT ( 11 ) DEFAULT NULL COMMENT '安全等级(0: 普通 1: 中等 2: 高风险)',
	`class_name` VARCHAR ( 255 ) DEFAULT NULL COMMENT '类名',
	`method_name` VARCHAR ( 255 ) DEFAULT NULL COMMENT '方法名',
	`params` text DEFAULT NULL COMMENT '参数',
	`return_value` text DEFAULT NULL COMMENT '返回值',
	`server_ip` VARCHAR ( 255 ) DEFAULT NULL COMMENT '服务器 IP',
	`client_ip` VARCHAR ( 255 ) DEFAULT NULL COMMENT '客户端 IP',
	`client_address` VARCHAR ( 255 ) DEFAULT NULL COMMENT '客户端地址',
	`browser` VARCHAR ( 255 ) DEFAULT NULL COMMENT '浏览器',
	`total_time_millis` BIGINT ( 20 ) DEFAULT NULL COMMENT '总时间毫秒数',
	`username` VARCHAR ( 255 ) DEFAULT NULL COMMENT '用户名',
    `operation_exception` BIT ( 1 ) DEFAULT NULL COMMENT '操作是否异常',
	`exception_detail` text COMMENT '异常信息',
	`create_time` datetime DEFAULT NULL COMMENT '创建时间',
	PRIMARY KEY ( `id` ) USING BTREE
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '系统日志';
```
- 添加依赖
```xml
<dependency>
    <groupId>com.fanxuankai.boot</groupId>
    <artifactId>fxk-boot-starter-log</artifactId>
    <version>${latestVersion}</version>
</dependency>
```

- 配置
```yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/fxk-boot?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver
log:
  # 存储方式
  store-type: jdbc
  jdbc-store:
    # 数据库表名
    table-name: sys_log
server:
  port: 80
```

- 使用  
具体使用见 Example