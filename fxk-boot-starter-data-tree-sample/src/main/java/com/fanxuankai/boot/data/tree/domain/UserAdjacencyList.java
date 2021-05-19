package com.fanxuankai.boot.data.tree.domain;

import com.fanxuankai.commons.extra.mybatis.tree.AdjacencyList;

/**
 * @author fanxuankai
 */
public class UserAdjacencyList implements AdjacencyList.Entity {
    private Long id;
    private Long pid;
    private String code;
    private String name;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
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
