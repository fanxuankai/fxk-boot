package com.fanxuankai.boot.data.tree.domain;

import com.fanxuankai.commons.extra.mybatis.base.BaseModel;
import com.fanxuankai.commons.extra.mybatis.tree.SimpleTreeNode;

/**
 * @author fanxuankai
 */
public class UserTree extends BaseModel implements SimpleTreeNode {
    /**
     * 父节点 id
     */
    private Long pid;
    /**
     * 层级
     */
    private Integer level;
    /**
     * 树 id
     */
    private Long treeId;
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;

    @Override
    public Long getPid() {
        return pid;
    }

    @Override
    public void setPid(Long pid) {
        this.pid = pid;
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
