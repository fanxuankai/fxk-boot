package com.fanxuankai.boot.admin.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 角色 数据传输对象
 *
 * @author fanxuankai
 */
@Data
@Accessors(chain = true)
public class RoleDTO implements Serializable {
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
}