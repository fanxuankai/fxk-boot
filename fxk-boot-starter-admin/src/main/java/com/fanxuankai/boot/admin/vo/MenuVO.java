package com.fanxuankai.boot.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 菜单 视图对象
 *
 * @author fanxuankai
 * @date 2021-06-04
 */
@Data
@Accessors(chain = true)
public class MenuVO implements Serializable {
    /**
     * 主键
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
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
    /**
     * 创建人
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long createUserId;
    /**
     * 修改人
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long modifiedUserId;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createDate;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp lastModifiedDate;
}