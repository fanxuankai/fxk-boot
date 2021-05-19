package com.fanxuankai.boot.data.tree.domain;

import com.fanxuankai.commons.extra.mybatis.tree.ClosureTable;

/**
 * @author fanxuankai
 */
public class UserClosureTable implements ClosureTable.Entity {
    private Long ancestor;
    private Long descendant;
    private Integer distance;

    @Override
    public Long getAncestor() {
        return ancestor;
    }

    @Override
    public void setAncestor(Long ancestor) {
        this.ancestor = ancestor;
    }

    @Override
    public Long getDescendant() {
        return descendant;
    }

    @Override
    public void setDescendant(Long descendant) {
        this.descendant = descendant;
    }

    @Override
    public Integer getDistance() {
        return distance;
    }

    @Override
    public void setDistance(Integer distance) {
        this.distance = distance;
    }
}
