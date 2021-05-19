package com.fanxuankai.boot.data.tree.domain;

import com.fanxuankai.commons.extra.mybatis.tree.PathEnumerations;

/**
 * @author fanxuankai
 */
public class UserPathEnumerations implements PathEnumerations.Entity {
    private Long id;
    private String path;
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
    public String getPath() {
        return path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
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
