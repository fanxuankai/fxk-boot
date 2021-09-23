package com.fanxuankai.boot.canal.elasticsearch.document;

import com.fanxuankai.boot.canal.elasticsearch.domain.Attribute;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

/**
 * @author fanxuankai
 */
@Document(indexName = "canal_client_example.product_info")
public class ProductInfo {
    @Id
    private Long id;
    private String code;
    private String name;
    private List<Attribute> attributeList;

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

    public List<Attribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<Attribute> attributeList) {
        this.attributeList = attributeList;
    }
}
