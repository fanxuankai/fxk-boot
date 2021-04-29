### 简介
工程代码中枚举的统一自动化管理

### Getting started
- 建表
```sql
CREATE TABLE `sys_enum_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '类名',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='枚举类';

CREATE TABLE `sys_enum` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type_id` bigint(20) DEFAULT NULL COMMENT '枚举类型id',
  `name` varchar(255) DEFAULT NULL COMMENT '枚举名',
  `code` tinyint(4) DEFAULT NULL COMMENT '枚举代码',
  `value` varchar(255) DEFAULT NULL COMMENT '枚举值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_type_name` (`type_id`,`name`) USING BTREE,
  UNIQUE KEY `uk_type_code` (`type_id`,`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='枚举';
```
- 支持自定义表名  
在 resources 目录下创建 table-info.json
```json
[
  {
    "className": "com.fanxuankai.boot.enums.domain.Enum",
    "tableName": "my_sys_enum"
  },
  {
    "className": "com.fanxuankai.boot.enums.domain.EnumType",
    "tableName": "my_sys_enum_type"
  }
]
```
- 添加依赖
```xml
<dependency>
    <groupId>com.fanxuankai.boot</groupId>
    <artifactId>fxk-boot-starter-enum</artifactId>
    <version>${enum.manager.spring.boot.version}</version>
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
```

- JSON 配置
```json
[
  {
    "enumType": {
      "name": "deleted",
      "description": "是否删除"
    },
    "enumList": [
      {
        "name": "no",
        "value": "未删除"
      },
      {
        "name": "yes",
        "value": "已删除"
      }
    ]
  },
  {
    "enumType": {
      "name": "colour",
      "description": "颜色"
    },
    "enumList": [
      {
        "name": "white",
        "value": "白色"
      },
      {
        "name": "red",
        "value": "红色"
      },
      {
        "name": "black",
        "value": "黑色"
      }
    ]
  }
]
```
- 运行
```
@Resource
private EnumGenerator enumGenerator;

GenerateModel model = new GenerateModel();
model.setAuth("fanxuankai");
model.setPath("项目绝对路径/src/main/java");
model.setPackageName("com.fanxuankai.boot.enums");
model.setGenerateDataOnly(false);
model.setIncrement(false);
enumGenerator.generate(model);
```

- 枚举工具使用
```
// 查找是否删除
EnumUtils.find(Deleted.class, 0).ifPresent(System.out::println);
// 查找颜色
System.out.println(EnumUtils.get(Colour.class, 0));
```