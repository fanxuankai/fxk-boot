package com.fanxuankai.boot.generator.strategy.model;

/**
 * @author fanxuankai
 */
public class VoTemplateData extends TemplateData {
    /**
     * 集成 EasyExcel
     */
    private boolean integrateEasyExcel;

    public boolean isIntegrateEasyExcel() {
        return integrateEasyExcel;
    }

    public void setIntegrateEasyExcel(boolean integrateEasyExcel) {
        this.integrateEasyExcel = integrateEasyExcel;
    }
}
