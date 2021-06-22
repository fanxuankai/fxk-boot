## 简介
Spring Boot Starter Elasticsearch

### Maven 依赖
```xml
<dependency>
    <groupId>com.fanxuankai.boot</groupId>
    <artifactId>fxk-boot-starter-elasticsearch</artifactId>
    <version>${latestVersion}</version>
</dependency>
```

### Spring Boot 配置
```yml
## application.yml
fxk:
  elasticsearch:
    host-and-ports: localhost:9200
    user:
    password:
    connect-timeout:
    socket-timeout:
```