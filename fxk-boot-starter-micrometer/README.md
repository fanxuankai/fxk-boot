### 简介
Micrometer 收集 Spring Boot 服务性能数据，供 Prometheus 获取数据，将数据展示在 Grafana，达到可视化性能监控

### Getting Started
- 添加依赖
```xml
<dependency>
    <groupId>com.fanxuankai.boot</groupId>
    <artifactId>fxk-boot-starter-micrometer</artifactId>
    <version>${latestVersion}</version>
</dependency>
```

- Boot 配置  
application.yml
```yml
# 可视化监控配置
management:
  endpoint:
    prometheus:
      # 提供 url 供 prometheus 抓取数据：/actuator/Prometheus
      enabled: true
  endpoints:
    web:
      exposure:
        include: "*"
  metrics:
    tags:
      application: ${spring.application.name}
```

- Prometheus 配置  
prometheus.yml
```yml
scrape_configs:
  - job_name: "demo"
    metrics_path: '/actuator/prometheus'
    static_configs:
      # 服务的 ip 地址，多个则以逗号隔开
      - targets: ["localhost:8080"]
```

- 启动 Prometheus  
./prometheus --config.file=prometheus.yml

- 启动 Grafana  
配置数据源，dashboard 使用 4701，具体步骤这里不赘述。