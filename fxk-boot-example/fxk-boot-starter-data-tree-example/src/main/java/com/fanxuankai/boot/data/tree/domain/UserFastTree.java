package com.fanxuankai.boot.data.tree.domain;

import com.fanxuankai.commons.extra.mybatis.tree.FastTree;

/**
 * @author fanxuankai
 */
public class UserFastTree implements FastTree.Entity {
    private Long id;
    private Long pid;
    private String code;
    private String name;
    private Integer level;
    private Long treeId;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getPid() {
        return pid;
    }

    @Override
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

    @Override
    public Integer getLevel() {
        return level;
    }

    @Override
    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public Long getTreeId() {
        return treeId;
    }

    @Override
    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }
}
