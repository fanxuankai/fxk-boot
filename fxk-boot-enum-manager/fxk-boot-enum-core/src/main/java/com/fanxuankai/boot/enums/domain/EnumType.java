package com.fanxuankai.boot.enums.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 枚举类型
 *
 * @author fanxuankai
 */
@Data
@Accessors(chain = true)
@TableName("sys_enum_type")
public class EnumType {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 枚举类名
     */
    private String name;
    /**
     * 枚举描述
     */
    private String description;
}
