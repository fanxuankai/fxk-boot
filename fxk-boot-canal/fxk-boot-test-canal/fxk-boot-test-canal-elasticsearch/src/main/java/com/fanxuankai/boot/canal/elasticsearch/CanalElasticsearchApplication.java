package com.fanxuankai.boot.canal.elasticsearch;

import com.fanxuankai.boot.canal.elasticsearch.annotation.IndexScan;
import com.fanxuankai.boot.canal.elasticsearch.domain.ProductAttribute;
import com.fanxuankai.boot.canal.elasticsearch.mapper.AttributeMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fanxuankai
 */
@SpringBootApplication
@IndexScan(basePackageClasses = {ProductAttribute.class})
@MapperScan(basePackageClasses = {AttributeMapper.class})
public class CanalElasticsearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(CanalElasticsearchApplication.class, args);
    }
}
