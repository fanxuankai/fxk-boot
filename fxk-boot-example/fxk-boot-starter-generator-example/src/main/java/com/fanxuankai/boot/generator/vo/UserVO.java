package com.fanxuankai.boot.generator.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 用户 视图对象
 *
 * @author fanxuankai
 */
@Data
@Accessors(chain = true)
public class UserVO implements Serializable {
    /**
     * ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    /**
     * 类型 枚举: materielType
     */
    private Integer type;
    /**
     * 子类型
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long subType;
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 型号
     */
    private String model;
    /**
     * 规格
     */
    private String specs;
    /**
     * 单位
     */
    private String unit;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 关联物料 id
     */
    private String relationId;
    /**
     * 关联物料 code
     */
    private String relationCode;
    /**
     * 关联物料名称
     */
    private String relationName;
    /**
     * 创建人
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long createUserId;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createDate;
    /**
     * 修改人
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long modifiedUserId;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp lastModifiedDate;
}