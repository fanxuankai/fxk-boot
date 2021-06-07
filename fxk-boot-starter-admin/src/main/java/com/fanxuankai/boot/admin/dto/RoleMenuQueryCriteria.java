package com.fanxuankai.boot.admin.dto;

import com.fanxuankai.commons.extra.mybatis.annotation.Query;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * 角色-菜单 查询条件
 *
 * @author fanxuankai
 */
@Data
@Accessors(chain = true)
public class RoleMenuQueryCriteria {
    /**
     * 主键 EQ
     */
    @Query(field = "id", type = Query.Type.EQ)
    private Long idEq;
    /**
     * 角色 id EQ
     */
    @Query(field = "roleId", type = Query.Type.EQ)
    private Long roleIdEq;
    /**
     * 菜单 id EQ
     */
    @Query(field = "menuId", type = Query.Type.EQ)
    private Long menuIdEq;
}