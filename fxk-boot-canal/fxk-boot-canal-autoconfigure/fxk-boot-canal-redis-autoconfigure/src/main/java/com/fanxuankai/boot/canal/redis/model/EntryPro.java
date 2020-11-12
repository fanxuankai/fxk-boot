package com.fanxuankai.boot.canal.redis.model;

import java.util.List;

/**
 * @author fanxuankai
 */
public class EntryPro {
    private String name;
    private List<Object> values;

    public EntryPro(String name, List<Object> values) {
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }
}
