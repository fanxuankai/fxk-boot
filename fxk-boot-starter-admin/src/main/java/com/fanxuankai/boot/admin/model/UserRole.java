package com.fanxuankai.boot.admin.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fanxuankai.commons.extra.mybatis.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 用户-角色 实体类
 *
 * @author fanxuankai
 * @date 2021-06-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user_role")
public class UserRole extends BaseModel {
    /**
     * 用户 id
     */
    @TableField(value = "user_id")
    private Long userId;
    /**
     * 角色 id
     */
    @TableField(value = "role_id")
    private Long roleId;
}