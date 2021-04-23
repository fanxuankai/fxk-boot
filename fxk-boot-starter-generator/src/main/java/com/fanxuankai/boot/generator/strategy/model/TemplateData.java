package com.fanxuankai.boot.generator.strategy.model;

import com.fanxuankai.boot.generator.model.GenConfig;

import java.time.LocalDate;
import java.util.List;

/**
 * @author fanxuankai
 */
public class TemplateData extends GenConfig {
    private final String date = LocalDate.now().toString();
    private String className;
    private String changeClassName;
    private boolean hasTimestamp;
    private boolean hasDate;
    private boolean hasLong;
    private boolean hasBigDecimal;
    private String pkCapitalFieldType;
    private String pkFieldName;
    private List<ColumnData> columns;

    public String getDate() {
        return date;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getChangeClassName() {
        return changeClassName;
    }

    public void setChangeClassName(String changeClassName) {
        this.changeClassName = changeClassName;
    }

    public boolean isHasTimestamp() {
        return hasTimestamp;
    }

    public void setHasTimestamp(boolean hasTimestamp) {
        this.hasTimestamp = hasTimestamp;
    }

    public boolean isHasDate() {
        return hasDate;
    }

    public void setHasDate(boolean hasDate) {
        this.hasDate = hasDate;
    }

    public boolean isHasLong() {
        return hasLong;
    }

    public void setHasLong(boolean hasLong) {
        this.hasLong = hasLong;
    }

    public boolean isHasBigDecimal() {
        return hasBigDecimal;
    }

    public void setHasBigDecimal(boolean hasBigDecimal) {
        this.hasBigDecimal = hasBigDecimal;
    }

    public String getPkCapitalFieldType() {
        return pkCapitalFieldType;
    }

    public void setPkCapitalFieldType(String pkCapitalFieldType) {
        this.pkCapitalFieldType = pkCapitalFieldType;
    }

    public String getPkFieldName() {
        return pkFieldName;
    }

    public void setPkFieldName(String pkFieldName) {
        this.pkFieldName = pkFieldName;
    }

    public List<ColumnData> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnData> columns) {
        this.columns = columns;
    }
}
