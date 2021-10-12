package com.fanxuankai.boot.generator.dto;

import com.fanxuankai.commons.extra.mybatis.annotation.Query;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 用户 查询条件
 *
 * @author admin
 */
@Data
@Accessors(chain = true)
public class UserQueryCriteria {
    /**
     * 主键 EQ
     */
    @Query(field = "id", type = Query.Type.EQ)
    private Long idEq;
    /**
     * 账号 EQ
     */
    @Query(field = "username", type = Query.Type.EQ)
    private String usernameEq;
    /**
     * 密码 EQ
     */
    @Query(field = "password", type = Query.Type.EQ)
    private String passwordEq;
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
     */
    @Query(field = "gmtCreate", type = Query.Type.EQ)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreateEq;
    /**
     * 修改时间 EQ
     */
    @Query(field = "gmtModified", type = Query.Type.EQ)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModifiedEq;
    /**
     * 是否删除 EQ
     */
    @Query(field = "deleted", type = Query.Type.EQ)
    private Integer deletedEq;
}