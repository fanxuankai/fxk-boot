package com.fanxuankai.boot.generator.dto;

import com.fanxuankai.commons.extra.mybatis.annotation.Query;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

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
     * ID EQ
     */
    @Query(field = "id", type = Query.Type.EQ)
    private Long idEq;
    /**
     * 类型 枚举: materielType EQ
     */
    @Query(field = "type", type = Query.Type.EQ)
    private Integer typeEq;
    /**
     * 子类型 EQ
     */
    @Query(field = "subType", type = Query.Type.EQ)
    private Long subTypeEq;
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
     * 型号 EQ
     */
    @Query(field = "model", type = Query.Type.EQ)
    private String modelEq;
    /**
     * 规格 EQ
     */
    @Query(field = "specs", type = Query.Type.EQ)
    private String specsEq;
    /**
     * 单位 EQ
     */
    @Query(field = "unit", type = Query.Type.EQ)
    private String unitEq;
    /**
     * 备注 EQ
     */
    @Query(field = "remarks", type = Query.Type.EQ)
    private String remarksEq;
    /**
     * 关联物料 id EQ
     */
    @Query(field = "relationId", type = Query.Type.EQ)
    private String relationIdEq;
    /**
     * 关联物料 code EQ
     */
    @Query(field = "relationCode", type = Query.Type.EQ)
    private String relationCodeEq;
    /**
     * 关联物料名称 EQ
     */
    @Query(field = "relationName", type = Query.Type.EQ)
    private String relationNameEq;
    /**
     * 创建人 EQ
     */
    @Query(field = "createUserId", type = Query.Type.EQ)
    private Long createUserIdEq;
    /**
     * 创建时间 EQ
     * GET 请求方式, 日期格式需要改为: MM/dd/yyyy ...
     */
    @Query(field = "gmtCreate", type = Query.Type.EQ)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreateEq;
    /**
     * 修改人 EQ
     */
    @Query(field = "modifiedUserId", type = Query.Type.EQ)
    private Long modifiedUserIdEq;
    /**
     * 修改时间 EQ
     * GET 请求方式, 日期格式需要改为: MM/dd/yyyy ...
     */
    @Query(field = "gmtModified", type = Query.Type.EQ)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModifiedEq;
    /**
     * 是否删除 枚举: yesOrNo EQ
     */
    @Query(field = "deleted", type = Query.Type.EQ)
    private Boolean deletedEq;
}