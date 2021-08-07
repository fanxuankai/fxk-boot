package com.fanxuankai.boot.admin.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fanxuankai.commons.extra.mybatis.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 角色 实体类
 *
 * @author fanxuankai
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("role")
public class Role extends BaseModel {
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
}