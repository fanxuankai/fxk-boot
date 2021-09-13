package com.fanxuankai.boot.generator.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户 视图对象
 *
 * @author admin
 */
@Data
@Accessors(chain = true)
public class UserVO implements Serializable {
    /**
     * 主键
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ExcelProperty("主键")
    private Long id;
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
    /**
     * 创建人
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ExcelProperty("创建人")
    private Long createUserId;
    /**
     * 修改人
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ExcelProperty("修改人")
    private Long modifiedUserId;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelProperty("创建时间")
    private Date gmtCreate;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ExcelProperty("修改时间")
    private Date gmtModified;
}