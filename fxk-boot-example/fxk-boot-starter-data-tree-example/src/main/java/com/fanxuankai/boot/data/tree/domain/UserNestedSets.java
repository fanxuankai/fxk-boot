package com.fanxuankai.boot.data.tree.domain;

import com.fanxuankai.commons.extra.mybatis.tree.NestedSets;

/**
 * @author fanxuankai
 */
public class UserNestedSets implements NestedSets.Entity {
    private Long id;
    private String code;
    private String name;
    private Long pid;
    private Long lft;
    private Long rgt;

    @Override
    public Long getId() {
        return id;
    }

    @Override
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

    @Override
    public Long getPid() {
        return pid;
    }

    @Override
    public void setPid(Long pid) {
        this.pid = pid;
    }

    @Override
    public Long getLft() {
        return lft;
    }

    @Override
    public void setLft(Long lft) {
        this.lft = lft;
    }

    @Override
    public Long getRgt() {
        return rgt;
    }

    @Override
    public void setRgt(Long rgt) {
        this.rgt = rgt;
    }
}
