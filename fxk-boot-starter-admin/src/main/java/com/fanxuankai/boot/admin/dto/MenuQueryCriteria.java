package com.fanxuankai.boot.admin.dto;

import com.fanxuankai.commons.extra.mybatis.annotation.Query;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * 菜单 查询条件
 *
 * @author fanxuankai
 * @date 2021-06-04
 */
@Data
@Accessors(chain = true)
public class MenuQueryCriteria {
    /**
     * 主键 EQ
     */
    @Query(field = "id", type = Query.Type.EQ)
    private Long idEq;
    /**
     * 类型(目录:0 菜单:1 按钮:2) EQ
     */
    @Query(field = "type", type = Query.Type.EQ)
    private Integer typeEq;
    /**
     * 标题 EQ
     */
    @Query(field = "title", type = Query.Type.EQ)
    private String titleEq;
    /**
     * 排序 EQ
     */
    @Query(field = "menuSort", type = Query.Type.EQ)
    private Integer menuSortEq;
    /**
     * 权限标识 EQ
     */
    @Query(field = "permission", type = Query.Type.EQ)
    private String permissionEq;
    /**
     * 上级菜单 EQ
     */
    @Query(field = "pid", type = Query.Type.EQ)
    private Long pidEq;
    /**
     * 组件名称 EQ
     */
    @Query(field = "componentName", type = Query.Type.EQ)
    private String componentNameEq;
    /**
     * 组件路径 EQ
     */
    @Query(field = "component", type = Query.Type.EQ)
    private String componentEq;
    /**
     * 路由地址 EQ
     */
    @Query(field = "path", type = Query.Type.EQ)
    private String pathEq;
    /**
     * 图标 EQ
     */
    @Query(field = "icon", type = Query.Type.EQ)
    private String iconEq;
    /**
     * 是否缓存 EQ
     */
    @Query(field = "cache", type = Query.Type.EQ)
    private Boolean cacheEq;
    /**
     * 是否隐藏 EQ
     */
    @Query(field = "hidden", type = Query.Type.EQ)
    private Boolean hiddenEq;
    /**
     * 创建人 EQ
     */
    @Query(field = "createUserId", type = Query.Type.EQ)
    private Long createUserIdEq;
    /**
     * 修改人 EQ
     */
    @Query(field = "modifiedUserId", type = Query.Type.EQ)
    private Long modifiedUserIdEq;
    /**
     * 创建时间 EQ
     * GET 请求方式, 日期格式需要改为: MM/dd/yyyy ...
     */
    @Query(field = "createDate", type = Query.Type.EQ)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createDateEq;
    /**
     * 修改时间 EQ
     * GET 请求方式, 日期格式需要改为: MM/dd/yyyy ...
     */
    @Query(field = "lastModifiedDate", type = Query.Type.EQ)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp lastModifiedDateEq;
    /**
     * 是否删除 EQ
     */
    @Query(field = "deleted", type = Query.Type.EQ)
    private Boolean deletedEq;
}