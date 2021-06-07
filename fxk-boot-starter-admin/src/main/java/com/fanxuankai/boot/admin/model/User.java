package com.fanxuankai.boot.admin.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fanxuankai.commons.extra.mybatis.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 用户 实体类
 *
 * @author fanxuankai
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user")
public class User extends BaseModel {
    /**
     * 账号
     */
    @TableField(value = "username")
    private String username;
    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;
    /**
     * 编号
     */
    @TableField(value = "no")
    private String no;
    /**
     * 姓名
     */
    @TableField(value = "full_name")
    private String fullName;
    /**
     * 性别(男:M 女:F)
     */
    @TableField(value = "gender")
    private String gender;
    /**
     * 生日
     */
    @TableField(value = "birthday")
    private Date birthday;
    /**
     * 证件号
     */
    @TableField(value = "id_no")
    private String idNo;
    /**
     * 证件类型
     */
    @TableField(value = "id_type")
    private String idType;
    /**
     * 昵称
     */
    @TableField(value = "nickname")
    private String nickname;
    /**
     * 密码修改时间
     */
    @TableField(value = "password_last_modified_date")
    private Timestamp passwordLastModifiedDate;
    /**
     * 移动电话
     */
    @TableField(value = "mobile")
    private String mobile;
    /**
     * 固定电话
     */
    @TableField(value = "phone")
    private String phone;
    /**
     * 地址
     */
    @TableField(value = "address")
    private String address;
    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;
    /**
     * QQ
     */
    @TableField(value = "qq")
    private String qq;
    /**
     * 微信
     */
    @TableField(value = "we_chat")
    private String weChat;
    /**
     * 头像文件名
     */
    @TableField(value = "avatar_name")
    private String avatarName;
    /**
     * 头像存储路径
     */
    @TableField(value = "avatar_path")
    private String avatarPath;
    /**
     * 部门 id
     */
    @TableField(value = "dept_id")
    private Long deptId;
    /**
     * 是否为管理员
     */
    @TableField(value = "admin")
    private Boolean admin;
    /**
     * 账号过期
     */
    @TableField(value = "account_expired")
    private Boolean accountExpired;
    /**
     * 账号被锁定
     */
    @TableField(value = "account_locked")
    private Boolean accountLocked;
    /**
     * 密码过期
     */
    @TableField(value = "credentials_expired")
    private Boolean credentialsExpired;
    /**
     * 是否激活
     */
    @TableField(value = "enabled")
    private Boolean enabled;
}