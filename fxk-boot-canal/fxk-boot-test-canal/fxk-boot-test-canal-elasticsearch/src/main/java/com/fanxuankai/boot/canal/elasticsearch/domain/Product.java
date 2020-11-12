package com.fanxuankai.boot.canal.elasticsearch.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fanxuankai.boot.canal.elasticsearch.document.ProductInfo;
import com.fanxuankai.boot.canal.elasticsearch.function.ProductFunction;
import com.fanxuankai.canal.elasticsearch.annotation.Index;
import com.fanxuankai.canal.elasticsearch.annotation.Indexes;

/**
 * @author fanxuankai
 */
@TableName(value = "t_product", schema = "canal_client_example")
@Indexes({
        @Index(documentClass = ProductInfo.class, documentFunctionClass = ProductFunction.class)
})
public class Product {
    private Long id;
    private String code;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
