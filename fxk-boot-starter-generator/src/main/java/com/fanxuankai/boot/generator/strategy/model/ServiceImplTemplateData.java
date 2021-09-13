package com.fanxuankai.boot.generator.strategy.model;

/**
 * @author fanxuankai
 */
public class ServiceImplTemplateData extends TemplateData {
    private String pkCapitalColName;
    /**
     * 集成 EasyExcel
     */
    private boolean integrateEasyExcel;

    public String getPkCapitalColName() {
        return pkCapitalColName;
    }

    public void setPkCapitalColName(String pkCapitalColName) {
        this.pkCapitalColName = pkCapitalColName;
    }

    public boolean isIntegrateEasyExcel() {
        return integrateEasyExcel;
    }

    public void setIntegrateEasyExcel(boolean integrateEasyExcel) {
        this.integrateEasyExcel = integrateEasyExcel;
    }
}
