package com.fanxuankai.boot.canal.elasticsearch.domain;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author fanxuankai
 */
@TableName(value = "t_attribute", schema = "canal_client_example")
public class Attribute {
    private Long id;
    private Integer type;
    private String name;
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
