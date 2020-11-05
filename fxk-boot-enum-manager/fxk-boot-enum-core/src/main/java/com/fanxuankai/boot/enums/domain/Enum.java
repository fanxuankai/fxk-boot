package com.fanxuankai.boot.enums.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 枚举
 *
 * @author fanxuankai
 */
@Data
@Accessors(chain = true)
@TableName("sys_enum")
public class Enum {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 类型id
     */
    private Long typeId;
    /**
     * 枚举名
     */
    private String name;
    /**
     * 枚举代码
     */
    private Integer code;
    /**
     * 枚举值
     */
    private String value;
}
