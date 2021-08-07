package com.fanxuankai.boot.enums.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Objects;

/**
 * 枚举
 *
 * @author fanxuankai
 */
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
    /**
     * 是否禁用
     */
    private boolean disabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Enum anEnum = (Enum) o;
        return disabled == anEnum.disabled &&
                name.equals(anEnum.name) &&
                code.equals(anEnum.code) &&
                value.equals(anEnum.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, code, value, disabled);
    }
}
