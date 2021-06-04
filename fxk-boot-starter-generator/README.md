## 简介
模板代码生成器, 为减少重复性工作, 大量提升工作效率, 终极目标是生成代码即可联调;  
针对 Mybatis-Plus 框架, 基于 FreeMarker 实现, 代码易维护易扩展, 方便二次开发;  
代码分层如下: 
- Api: API 接口
- DTO: 数据传输层
- QueryCriteria: 查询条件(跟 DTO 同一个包), 结合 @Query、QueryHelp 可以自动构造 Wrapper, 避免重复的 Wrapper 构造
- VO: 视图对象层
- ApiImpl: API 实现类
- Controller: 控制器层
- Dao: 数据访问接口
- DaoImpl: 数据访问实现类
- Entity: 实体类
- Mapper: MyBatis-Plus Mapper 接口
- MapStructMapper: 对象转换层, MapStruct Mapper 接口, 对象转换只需要定义接口, 框架编译生成实现类, 这样一来项目中大量对象转换的代码得到更好的封装
- Service: 服务接口
- ServiceImpl: 服务实现类

## Getting Started
- application.yml code-generator 配置, 然后指定 code-generator.tables 生成哪些表的代码
- resources/config 目录下增加表配置文件, 或者启动应用会自动生成配置文件
- 启动应用生成代码

## 配置
```yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/${code-generator.schema}?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver
# 代码生成配置
code-generator:
  # 数据库
  schema: code_generator
  # 需要生成的表
  tables: t_user
  # 作者
  author: fanxuankai
  # 去表前缀
  prefix: t_
  # 项目绝对路径
  project-dir: /Users/fanxuankai/Java/Workspace/myproject/fanxuankai/framework/标准/fxk-boot/fxk-boot-starter-generator-sample
  # API 相对路径
  api-path:
  # WEB 相对路径
  web-path:
  # 服务名
  service-name: user-service
  # 包名
  package-name: com.fanxuankai.boot.generator
  # 是否覆盖
  cover: true
  # 自动填值 { 列名: INSERT|UPDATE|INSERT_UPDATE }
  auto-fill:
    create_user_id: INSERT
    create_date: INSERT
    modified_user_id: UPDATE
    last_modified_date: UPDATE
    deleted: INSERT
  # dto 不显示的字段
  form-exclude-columns: create_user_id,create_date,modified_user_id,last_modified_date,deleted
  # vo 不显示的字段
  list-exclude-columns: deleted
  # 父类字段
  inherited-columns: id,create_user_id,create_date,modified_user_id,last_modified_date,deleted
```