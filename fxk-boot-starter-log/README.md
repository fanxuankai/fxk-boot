### 简介
日志工具  
自定义注解, 基于 Spring AOP 实现方法拦截, 支持 Logger、Jdbc 方式保存日志信息, 默认是 Logger 方式

### Getting started
- 建表(Jdbc 方式)
```sql
DROP TABLE
IF
	EXISTS sys_log;
CREATE TABLE `sys_log` (
	`id` BIGINT ( 20 ) NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`description` VARCHAR ( 255 ) DEFAULT NULL COMMENT '日志描述',
	`log_level` VARCHAR ( 255 ) DEFAULT NULL COMMENT 'log 等级(INFO, ERROR)',
	`class_name` VARCHAR ( 255 ) DEFAULT NULL COMMENT '类名',
	`method_name` VARCHAR ( 255 ) DEFAULT NULL COMMENT '方法名',
	`params` text DEFAULT NULL COMMENT '参数',
	`client_ip` VARCHAR ( 255 ) DEFAULT NULL COMMENT '客户端 IP',
	`client_address` VARCHAR ( 255 ) DEFAULT NULL COMMENT '客户端地址',
	`time` BIGINT ( 20 ) DEFAULT NULL COMMENT '耗时(s)',
	`username` VARCHAR ( 255 ) DEFAULT NULL COMMENT '用户名',
	`browser` VARCHAR ( 255 ) DEFAULT NULL COMMENT '浏览器',
	`exception_detail` text COMMENT '异常信息',
	`create_time` datetime DEFAULT NULL COMMENT '创建时间',
	PRIMARY KEY ( `id` ) USING BTREE,
	KEY `log_create_time_index` ( `create_time` ) USING BTREE,
KEY `inx_log_type` ( `log_level` ) USING BTREE 
) ENGINE = INNODB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8 COMMENT = '系统日志';
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