package com.fanxuankai.boot.generator.strategy.model;

import com.fanxuankai.boot.generator.model.ColumnInfo;

/**
 * @author fanxuankai
 */
public class ColumnData extends ColumnInfo {
    /**
     * 对应的字段名类型
     */
    private String fieldType;
    /**
     * 对应的字段名, 首字母大写
     */
    private String capitalFieldName;
    /**
     * 查询方式, 驼峰且首字母大写
     */
    private String capitalQueryType;

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getCapitalFieldName() {
        return capitalFieldName;
    }

    public void setCapitalFieldName(String capitalFieldName) {
        this.capitalFieldName = capitalFieldName;
    }

    public String getCapitalQueryType() {
        return capitalQueryType;
    }

    public void setCapitalQueryType(String capitalQueryType) {
        this.capitalQueryType = capitalQueryType;
    }
}
