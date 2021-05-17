//package com.fanxuankai.boot.data.tree.domain;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.fanxuankai.commons.extra.mybatis.tree1.ClosureTable;
//
///**
// * @author fanxuankai
// */
//public class UserClosureTable implements ClosureTable {
//    @TableId(type = IdType.ASSIGN_ID)
//    private Long id;
//    private Long ancestor;
//    private Long descendant;
//    private Integer distance;
//
//    @Override
//    public Long getId() {
//        return id;
//    }
//
//    @Override
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    @Override
//    public Long getAncestor() {
//        return ancestor;
//    }
//
//    @Override
//    public void setAncestor(Long ancestor) {
//        this.ancestor = ancestor;
//    }
//
//    @Override
//    public Long getDescendant() {
//        return descendant;
//    }
//
//    @Override
//    public void setDescendant(Long descendant) {
//        this.descendant = descendant;
//    }
//
//    @Override
//    public Integer getDistance() {
//        return distance;
//    }
//
//    @Override
//    public void setDistance(Integer distance) {
//        this.distance = distance;
//    }
//}
