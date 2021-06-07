package com.fanxuankai.boot.admin.dto;

import com.fanxuankai.commons.extra.mybatis.annotation.Query;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 用户 查询条件
 *
 * @author fanxuankai
 */
@Data
@Accessors(chain = true)
public class UserQueryCriteria {
    /**
     * 主键 EQ
     */
    @Query(field = "id", type = Query.Type.EQ)
    private Long idEq;
    /**
     * 账号 EQ
     */
    @Query(field = "username", type = Query.Type.EQ)
    private String usernameEq;
    /**
     * 密码 EQ
     */
    @Query(field = "password", type = Query.Type.EQ)
    private String passwordEq;
    /**
     * 编号 EQ
     */
    @Query(field = "no", type = Query.Type.EQ)
    private String noEq;
    /**
     * 姓名 EQ
     */
    @Query(field = "fullName", type = Query.Type.EQ)
    private String fullNameEq;
    /**
     * 性别(男:M 女:F) EQ
     */
    @Query(field = "gender", type = Query.Type.EQ)
    private String genderEq;
    /**
     * 生日 EQ
     * GET 请求方式, 日期格式需要改为: MM/dd/yyyy ...
     */
    @Query(field = "birthday", type = Query.Type.EQ)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date birthdayEq;
    /**
     * 证件号 EQ
     */
    @Query(field = "idNo", type = Query.Type.EQ)
    private String idNoEq;
    /**
     * 证件类型 EQ
     */
    @Query(field = "idType", type = Query.Type.EQ)
    private String idTypeEq;
    /**
     * 昵称 EQ
     */
    @Query(field = "nickname", type = Query.Type.EQ)
    private String nicknameEq;
    /**
     * 密码修改时间 EQ
     * GET 请求方式, 日期格式需要改为: MM/dd/yyyy ...
     */
    @Query(field = "passwordLastModifiedDate", type = Query.Type.EQ)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp passwordLastModifiedDateEq;
    /**
     * 移动电话 EQ
     */
    @Query(field = "mobile", type = Query.Type.EQ)
    private String mobileEq;
    /**
     * 固定电话 EQ
     */
    @Query(field = "phone", type = Query.Type.EQ)
    private String phoneEq;
    /**
     * 地址 EQ
     */
    @Query(field = "address", type = Query.Type.EQ)
    private String addressEq;
    /**
     * 邮箱 EQ
     */
    @Query(field = "email", type = Query.Type.EQ)
    private String emailEq;
    /**
     * QQ EQ
     */
    @Query(field = "qq", type = Query.Type.EQ)
    private String qqEq;
    /**
     * 微信 EQ
     */
    @Query(field = "weChat", type = Query.Type.EQ)
    private String weChatEq;
    /**
     * 头像文件名 EQ
     */
    @Query(field = "avatarName", type = Query.Type.EQ)
    private String avatarNameEq;
    /**
     * 头像存储路径 EQ
     */
    @Query(field = "avatarPath", type = Query.Type.EQ)
    private String avatarPathEq;
    /**
     * 部门 id EQ
     */
    @Query(field = "deptId", type = Query.Type.EQ)
    private Long deptIdEq;
    /**
     * 是否为管理员 EQ
     */
    @Query(field = "admin", type = Query.Type.EQ)
    private Boolean adminEq;
    /**
     * 账号未过期 EQ
     */
    @Query(field = "accountNonExpired", type = Query.Type.EQ)
    private Boolean accountNonExpiredEq;
    /**
     * 账号未被锁定 EQ
     */
    @Query(field = "accountNonLocked", type = Query.Type.EQ)
    private Boolean accountNonLockedEq;
    /**
     * 密码未过期 EQ
     */
    @Query(field = "credentialsNonExpired", type = Query.Type.EQ)
    private Boolean credentialsNonExpiredEq;
    /**
     * 是否激活 EQ
     */
    @Query(field = "enabled", type = Query.Type.EQ)
    private Boolean enabledEq;
    /**
     * 创建人 EQ
     */
    @Query(field = "createUserId", type = Query.Type.EQ)
    private Long createUserIdEq;
    /**
     * 修改人 EQ
     */
    @Query(field = "modifiedUserId", type = Query.Type.EQ)
    private Long modifiedUserIdEq;
    /**
     * 创建时间 EQ
     * GET 请求方式, 日期格式需要改为: MM/dd/yyyy ...
     */
    @Query(field = "createDate", type = Query.Type.EQ)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createDateEq;
    /**
     * 修改时间 EQ
     * GET 请求方式, 日期格式需要改为: MM/dd/yyyy ...
     */
    @Query(field = "lastModifiedDate", type = Query.Type.EQ)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp lastModifiedDateEq;
    /**
     * 是否删除 EQ
     */
    @Query(field = "deleted", type = Query.Type.EQ)
    private Boolean deletedEq;
}