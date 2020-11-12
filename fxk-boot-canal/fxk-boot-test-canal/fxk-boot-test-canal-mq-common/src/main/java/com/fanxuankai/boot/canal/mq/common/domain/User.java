package com.fanxuankai.boot.canal.mq.common.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fanxuankai.canal.core.annotation.CanalTable;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author fanxuankai
 */
@Data
@Accessors(chain = true)
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
}