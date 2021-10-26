package com.fanxuankai.boot.generator.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 用户 实体类
 *
 * @author admin
 */
@Data
@Accessors(chain = true)
public class User {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;
    /**
     * 修改人
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long modifiedUserId;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date gmtModified;
    /**
     * 是否删除
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;
}