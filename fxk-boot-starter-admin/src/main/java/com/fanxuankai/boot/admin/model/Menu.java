package com.fanxuankai.boot.admin.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fanxuankai.commons.extra.mybatis.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 菜单 实体类
 *
 * @author fanxuankai
 * @date 2021-06-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("menu")
public class Menu extends BaseModel {
    /**
     * 类型(目录:0 菜单:1 按钮:2)
     */
    @TableField(value = "type")
    private Integer type;
    /**
     * 标题
     */
    @TableField(value = "title")
    private String title;
    /**
     * 排序
     */
    @TableField(value = "menu_sort")
    private Integer menuSort;
    /**
     * 权限标识
     */
    @TableField(value = "permission")
    private String permission;
    /**
     * 上级菜单
     */
    @TableField(value = "pid")
    private Long pid;
    /**
     * 组件名称
     */
    @TableField(value = "component_name")
    private String componentName;
    /**
     * 组件路径
     */
    @TableField(value = "component")
    private String component;
    /**
     * 路由地址
     */
    @TableField(value = "path")
    private String path;
    /**
     * 图标
     */
    @TableField(value = "icon")
    private String icon;
    /**
     * 是否缓存
     */
    @TableField(value = "cache")
    private Boolean cache;
    /**
     * 是否隐藏
     */
    @TableField(value = "hidden")
    private Boolean hidden;
}