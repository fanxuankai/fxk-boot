package com.fanxuankai.boot.generator.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fanxuankai.commons.extra.mybatis.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户 实体类
 *
 * @author fanxuankai
 * @date 2021-06-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_user")
public class User extends BaseModel {
    /**
     * 类型 枚举: materielType
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 子类型
     */
    @TableField(value = "sub_type")
    private Long subType;

    /**
     * 编码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 型号
     */
    @TableField(value = "model")
    private String model;

    /**
     * 规格
     */
    @TableField(value = "specs")
    private String specs;

    /**
     * 单位
     */
    @TableField(value = "unit")
    private String unit;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    private String remarks;

    /**
     * 关联物料 id
     */
    @TableField(value = "relation_id")
    private String relationId;

    /**
     * 关联物料 code
     */
    @TableField(value = "relation_code")
    private String relationCode;

    /**
     * 关联物料名称
     */
    @TableField(value = "relation_name")
    private String relationName;

}