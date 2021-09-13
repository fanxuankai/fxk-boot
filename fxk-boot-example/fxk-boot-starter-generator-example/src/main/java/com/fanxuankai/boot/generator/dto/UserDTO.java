package com.fanxuankai.boot.generator.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户 数据传输对象
 *
 * @author admin
 */
@Data
@Accessors(chain = true)
public class UserDTO implements Serializable {
    /**
     * 账号
     */
    @ExcelProperty("账号")
    private String username;
    /**
     * 密码
     */
    @ExcelProperty("密码")
    private String password;
}