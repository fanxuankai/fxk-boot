package com.fanxuankai.boot.admin.dto;

import com.fanxuankai.commons.extra.mybatis.annotation.Query;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * 用户-角色 查询条件
 *
 * @author fanxuankai
 * @date 2021-06-04
 */
@Data
@Accessors(chain = true)
public class UserRoleQueryCriteria {
    /**
     * 主键 EQ
     */
    @Query(field = "id", type = Query.Type.EQ)
    private Long idEq;
    /**
     * 用户 id EQ
     */
    @Query(field = "userId", type = Query.Type.EQ)
    private Long userIdEq;
    /**
     * 角色 id EQ
     */
    @Query(field = "roleId", type = Query.Type.EQ)
    private Long roleIdEq;
}