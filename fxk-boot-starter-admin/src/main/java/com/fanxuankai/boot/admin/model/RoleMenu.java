package com.fanxuankai.boot.admin.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fanxuankai.commons.extra.mybatis.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 角色-菜单 实体类
 *
 * @author fanxuankai
 * @date 2021-06-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("role_menu")
public class RoleMenu extends BaseModel {
    /**
     * 角色 id
     */
    @TableField(value = "role_id")
    private Long roleId;
    /**
     * 菜单 id
     */
    @TableField(value = "menu_id")
    private Long menuId;
}