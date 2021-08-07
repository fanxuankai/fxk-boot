package com.fanxuankai.boot.admin.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 菜单 数据传输对象
 *
 * @author fanxuankai
 */
@Data
@Accessors(chain = true)
public class MenuDTO implements Serializable {
    /**
     * 类型(目录:0 菜单:1 按钮:2)
     */
    private Integer type;
    /**
     * 标题
     */
    private String title;
    /**
     * 排序
     */
    private Integer menuSort;
    /**
     * 权限标识
     */
    private String permission;
    /**
     * 上级菜单
     */
    private Long pid;
    /**
     * 组件名称
     */
    private String componentName;
    /**
     * 组件路径
     */
    private String component;
    /**
     * 路由地址
     */
    private String path;
    /**
     * 图标
     */
    private String icon;
    /**
     * 是否缓存
     */
    private Boolean cache;
    /**
     * 是否隐藏
     */
    private Boolean hidden;
}