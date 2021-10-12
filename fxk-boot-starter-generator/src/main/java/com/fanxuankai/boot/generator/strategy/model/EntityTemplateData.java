package com.fanxuankai.boot.generator.strategy.model;

/**
 * @author fanxuankai
 */
public class EntityTemplateData extends TemplateData {
    private boolean fill;
    private boolean hasPrimaryKey;
    private boolean inherit;

    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    public boolean isHasPrimaryKey() {
        return hasPrimaryKey;
    }

    public void setHasPrimaryKey(boolean hasPrimaryKey) {
        this.hasPrimaryKey = hasPrimaryKey;
    }

    public boolean isInherit() {
        return inherit;
    }

    public void setInherit(boolean inherit) {
        this.inherit = inherit;
    }
}
