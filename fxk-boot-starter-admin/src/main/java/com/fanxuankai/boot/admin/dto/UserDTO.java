package com.fanxuankai.boot.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 用户 数据传输对象
 *
 * @author fanxuankai
 */
@Data
@Accessors(chain = true)
public class UserDTO implements Serializable {
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 编号
     */
    private String no;
    /**
     * 姓名
     */
    private String fullName;
    /**
     * 性别(男:M 女:F)
     */
    private String gender;
    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date birthday;
    /**
     * 证件号
     */
    private String idNo;
    /**
     * 证件类型
     */
    private String idType;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 密码修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp passwordLastModifiedDate;
    /**
     * 移动电话
     */
    private String mobile;
    /**
     * 固定电话
     */
    private String phone;
    /**
     * 地址
     */
    private String address;
    /**
     * 邮箱
     */
    private String email;
    /**
     * QQ
     */
    private String qq;
    /**
     * 微信
     */
    private String weChat;
    /**
     * 头像文件名
     */
    private String avatarName;
    /**
     * 头像存储路径
     */
    private String avatarPath;
    /**
     * 部门 id
     */
    private Long deptId;
    /**
     * 是否为管理员
     */
    private Boolean admin;
    /**
     * 账号未过期
     */
    private Boolean accountNonExpired;
    /**
     * 账号未被锁定
     */
    private Boolean accountNonLocked;
    /**
     * 密码未过期
     */
    private Boolean credentialsNonExpired;
    /**
     * 是否激活
     */
    private Boolean enabled;
}