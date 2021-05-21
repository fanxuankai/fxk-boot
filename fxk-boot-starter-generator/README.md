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
# 表名
tableName: t_user
# 表注释
comment: 用户
# 作者
author: fanxuankai
# 去表前缀
prefix: t_
# 项目路径
projectDir: "/Users/fanxuankai/Java/Workspace/runlion/ehm-app/ehm-business"
# API 路径
apiPath: ehm-business-api
# WEB 路径
webPath: ehm-business-web
# 服务名
serviceName: ehm-business
# 包名
packageName: com.runlion.ehm.business
# 是否覆盖已有文件
cover: true
# 需要生成代码的模板, 默认生成所有代码
templates: []
# 字段配置
columnInfos:
    # 列名
  - columnName: id
    # 列类型
    columnType: bigint
    # 对应的字段名, 默认为列名转驼峰
    fieldName:
    # 是否主键
    primaryKey: true
    # 是否唯一, 如果是 DAO 接口会生成相应的方法
    unique: false
    # 字段额外的参数 auto_increment 代表自增
    extra:
    # 列描述
    remark: ID
    # 是否必填
    notNull: true
    # 查询方式, 多个以逗号隔开 EQ | GE | LE | LIKE | NOT_LIKE | LIKE_LEFT | LIKE_RIGHT | GT | LT | IN | NOT_IN | NE | BETWEEN | NOT_BETWEEN | NOT_NULL | IS_NULL | ORDER_BY_ASC | ORDER_BY_DESC
    queryType: EQ
    # 自动填值 INSERT | UPDATE | INSERT_UPDATE
    fill: INSERT
    # 表单是否显示(dto)
    formShow: true
    # 列表是否显示(vo)
    listShow: true
```