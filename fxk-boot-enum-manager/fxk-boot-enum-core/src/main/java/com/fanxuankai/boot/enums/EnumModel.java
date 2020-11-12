package com.fanxuankai.boot.enums;

/**
 * @author fanxuankai
 */
public class EnumModel {
    private String packageName;
    private String auth;
    private EnumVO enumVO;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public EnumVO getEnumVO() {
        return enumVO;
    }

    public void setEnumVO(EnumVO enumVO) {
        this.enumVO = enumVO;
    }
}
