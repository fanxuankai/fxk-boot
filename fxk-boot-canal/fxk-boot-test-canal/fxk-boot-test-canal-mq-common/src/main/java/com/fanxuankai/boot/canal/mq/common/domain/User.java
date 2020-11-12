package com.fanxuankai.boot.canal.mq.common.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fanxuankai.canal.core.annotation.CanalTable;

import java.time.LocalDateTime;

/**
 * @author fanxuankai
 */
@CanalTable(schema = "canal_client_example", name = "t_user")
@TableName("t_user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long createdBy;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;
    private Long lastModifiedBy;
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime lastModifiedDate;
    @Version
    private Integer version;
    private String phone;
    private String username;
    private String password;
    private Integer type;
    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Long getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}