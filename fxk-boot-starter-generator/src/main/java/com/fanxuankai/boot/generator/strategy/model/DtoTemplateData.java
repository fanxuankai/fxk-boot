package com.fanxuankai.boot.generator.strategy.model;

/**
 * @author fanxuankai
 */
public class DtoTemplateData extends TemplateData {
    private boolean hasNotNull;
    /**
     * 集成 EasyExcel
     */
    private boolean integrateEasyExcel;

    public boolean isHasNotNull() {
        return hasNotNull;
    }

    public void setHasNotNull(boolean hasNotNull) {
        this.hasNotNull = hasNotNull;
    }

    public boolean isIntegrateEasyExcel() {
        return integrateEasyExcel;
    }

    public void setIntegrateEasyExcel(boolean integrateEasyExcel) {
        this.integrateEasyExcel = integrateEasyExcel;
    }
}
