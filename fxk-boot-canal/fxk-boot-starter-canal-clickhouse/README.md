## Getting started

- 添加 maven 依赖
```xml
<dependency>
    <groupId>com.fanxuankai.boot</groupId>
    <artifactId>fxk-boot-starter-canal-clickhouse</artifactId>
    <version>${latestVersion}</version>
</dependency>
```
- 配置
```yml
## application.yml
spring:
  datasource:
    url: jdbc:clickhouse://192.168.173.201:8123
    driver-class-name: ru.yandex.clickhouse.ClickHouseDriver
fxk:
  canal:
    clickhouse:
      configuration:
        instance: canalClickhouseExample
        filter: clickhouse_demo.user,clickhouse_demo.dept,clickhouse_demo.post
        show-entry-log: false
        show-event-log: true
        batch-size: 100
        parallel: true
        merge-entry:
          merge: true
          max-row-data-size:
            INSERT: 1000
            UPDATE: 10000
            DELETE: 10000
        skip: false
      enabled: true
      consumer-config-map:
        clickhouse_demo:
          user:
            ignore-change-columns: date
          dept:
            ignore-change-columns: date
          post:
            ignore-change-columns: date
      schema-name: default
```

## Example
[canal-clickhouse-test](https://github.com/fanxuankai/fxk-boot/tree/main/fxk-boot-canal/fxk-boot-test-canal/fxk-boot-test-canal-clickhouse)