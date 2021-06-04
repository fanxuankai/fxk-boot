package com.fanxuankai.boot.admin.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 角色 数据传输对象
 *
 * @author fanxuankai
 * @date 2021-06-04
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