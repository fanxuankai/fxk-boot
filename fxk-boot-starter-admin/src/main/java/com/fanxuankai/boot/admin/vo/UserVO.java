package com.fanxuankai.boot.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 用户 视图对象
 *
 * @author fanxuankai
 */
@Data
@Accessors(chain = true)
public class UserVO implements Serializable {
    /**
     * 主键
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    /**
     * 账号
     */
    private String username;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long deptId;
    /**
     * 是否为管理员
     */
    private Boolean admin;
    /**
     * 账号过期
     */
    private Boolean accountExpired;
    /**
     * 账号被锁定
     */
    private Boolean accountLocked;
    /**
     * 密码过期
     */
    private Boolean credentialsExpired;
    /**
     * 是否激活
     */
    private Boolean enabled;
    /**
     * 创建人
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long createUserId;
    /**
     * 修改人
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long modifiedUserId;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createDate;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp lastModifiedDate;
}