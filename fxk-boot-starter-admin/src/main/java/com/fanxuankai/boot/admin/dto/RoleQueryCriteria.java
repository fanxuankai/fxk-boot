package com.fanxuankai.boot.admin.dto;

import com.fanxuankai.commons.extra.mybatis.annotation.Query;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * 角色 查询条件
 *
 * @author fanxuankai
 * @date 2021-06-04
 */
@Data
@Accessors(chain = true)
public class RoleQueryCriteria {
    /**
     * 主键 EQ
     */
    @Query(field = "id", type = Query.Type.EQ)
    private Long idEq;
    /**
     * 编码 EQ
     */
    @Query(field = "code", type = Query.Type.EQ)
    private String codeEq;
    /**
     * 名称 EQ
     */
    @Query(field = "name", type = Query.Type.EQ)
    private String nameEq;
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