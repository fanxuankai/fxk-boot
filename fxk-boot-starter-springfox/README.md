## 简介
Spring Boot Starter Swagger

### Maven 依赖
```xml
<dependency>
    <groupId>com.fanxuankai.boot</groupId>
    <artifactId>fxk-boot-starter-springfox</artifactId>
    <version>${latestVersion}</version>
</dependency>
```

### Spring Boot 配置
```yml
## application.yml
swagger:
  docket:
    enabled: true
    host: http://localhost:${server.port}
    api-info:
      name: ${spring.application.name}
      version: v1.0.2
      description: Api Documentation
    apis:
      base-package: com.xxx.controller
    headers: Authorization
```

### 启动服务
http://localhost:port/swagger-ui/index.html