package com.fanxuankai.boot.enums.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Objects;

/**
 * 枚举类型
 *
 * @author fanxuankai
 */
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
    /**
     * 只生成数据
     */
    private boolean generateDataOnly;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isGenerateDataOnly() {
        return generateDataOnly;
    }

    public void setGenerateDataOnly(boolean generateDataOnly) {
        this.generateDataOnly = generateDataOnly;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EnumType enumType = (EnumType) o;
        return generateDataOnly == enumType.generateDataOnly &&
                name.equals(enumType.name) &&
                description.equals(enumType.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, generateDataOnly);
    }
}
