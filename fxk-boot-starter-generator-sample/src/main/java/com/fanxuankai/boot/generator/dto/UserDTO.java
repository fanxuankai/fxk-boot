package com.fanxuankai.boot.generator.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户 数据传输对象
 *
 * @author fanxuankai
 * @date 2021-05-21
 */
@Data
@Accessors(chain = true)
public class UserDTO implements Serializable {
    /**
     * 类型 枚举: materielType
     */
    private Integer type;

    /**
     * 子类型
     */
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

}