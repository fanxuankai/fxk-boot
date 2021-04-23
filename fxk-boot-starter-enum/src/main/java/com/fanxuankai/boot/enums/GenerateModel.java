package com.fanxuankai.boot.enums;

/**
 * 代码生成 model
 *
 * @author fanxuankai
 */
public class GenerateModel {
    /**
     * 作者
     */
    private String auth;
    /**
     * 枚举所在包
     */
    private String packageName;
    /**
     * 文件路径
     */
    private String path;
    /**
     * 增量模式,默认为全量模式
     */
    private boolean increment;
    /**
     * 只生成数据
     */
    private boolean generateDataOnly;

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isIncrement() {
        return increment;
    }

    public void setIncrement(boolean increment) {
        this.increment = increment;
    }

    public boolean isGenerateDataOnly() {
        return generateDataOnly;
    }

    public void setGenerateDataOnly(boolean generateDataOnly) {
        this.generateDataOnly = generateDataOnly;
    }
}
