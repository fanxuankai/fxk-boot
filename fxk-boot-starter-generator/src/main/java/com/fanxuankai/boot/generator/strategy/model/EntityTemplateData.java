package com.fanxuankai.boot.generator.strategy.model;

/**
 * @author fanxuankai
 */
public class EntityTemplateData extends TemplateData {
    private boolean auto;
    private boolean fill;

    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }
}
