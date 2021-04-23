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
    url: jdbc:mysql://localhost:3306/canal_client_example?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: HzB!OPxxE$5CwJIZ
    driver-class-name: com.mysql.cj.jdbc.Driver
```

- Usage
```
@Resource
private EnumService enumService;
@Resource
private EnumGenerator enumGenerator;

// 创建颜色、是否删除枚举, 方式一
List<EnumDTO.Enum> list = new ArrayList<>(3);
list.add(new EnumDTO.Enum().setName("white").setValue("白色"));
list.add(new EnumDTO.Enum().setName("red").setValue("红色"));
list.add(new EnumDTO.Enum().setName("black").setValue("黑色"));
enumService.add(new EnumDTO().setEnumType(new EnumDTO.EnumType().setName("colour").setDescription("颜色")).setEnumList(list));
list = new ArrayList<>(2);
list.add(new EnumDTO.Enum().setName("no").setValue("未删除"));
list.add(new EnumDTO.Enum().setName("yes").setValue("已删除"));
enumService.add(new EnumDTO().setEnumType(new EnumDTO.EnumType().setName("deleted").setDescription("是否删除")).setEnumList(list));

// 创建颜色、是否删除枚举, 方式二
JSON.parseArray(json, EnumDTO.class).forEach(enumDTO -> enumService.add(enumDTO));
json 格式如下:
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

// 生成枚举类
enumGenerator.generate(new GenerateModel()
                .setAuth("fanxuankai")
                .setPath("项目绝对路径/src/main/java")
                .setPackageName("com.fanxuankai.boot.enums"));
```

- 枚举使用
```
// 查找是否删除
EnumManager.find(Deleted.class, 0).ifPresent(System.out::println);
// 查找颜色
System.out.println(EnumManager.get(Colour.class, 0));
```