package com.fanxuankai.boot.data.tree.domain;

import com.fanxuankai.commons.extra.mybatis.base.BaseModel;
import com.fanxuankai.commons.extra.mybatis.tree.PathTreeNode;

/**
 * @author fanxuankai
 */
public class UserPathTree extends BaseModel implements PathTreeNode {
    /**
     * 父节点 id
     */
    private Long pid;
    /**
     * 层级
     */
    private Integer level;
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    private String path;

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
    public String getPath() {
        return path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }
}
