package com.fanxuankai.boot.generator.model;

import java.io.Serializable;

/**
 * 列的数据信息
 *
 * @author fanxuankai
 */
public class ColumnInfo implements Serializable {

    /**
     * 列名
     */
    private String columnName;

    /**
     * 列类型
     */
    private String columnType;

    /**
     * 对应的字段名, 默认为列名转驼峰
     */
    private String fieldName;

    /**
     * 是否主键
     */
    private boolean primaryKey;

    /**
     * 是否唯一, 如果是 DAO 接口会生成相应的方法
     */
    private boolean unique;

    /**
     * 字段额外的参数
     */
    private String extra;

    /**
     * 列描述
     */
    private String remark;

    /**
     * 是否必填
     */
    private boolean notNull;

    /**
     * 查询方式 枚举: com.fanxuankai.commons.extra.mybatis.annotation.Query.Type
     */
    private String queryType;

    /**
     * 自动填值
     */
    private String fill;

    /**
     * 表单是否显示
     */
    private boolean formShow;

    /**
     * 列表是否展示
     */
    private boolean listShow;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

    public boolean isFormShow() {
        return formShow;
    }

    public void setFormShow(boolean formShow) {
        this.formShow = formShow;
    }

    public boolean isListShow() {
        return listShow;
    }

    public void setListShow(boolean listShow) {
        this.listShow = listShow;
    }
}
