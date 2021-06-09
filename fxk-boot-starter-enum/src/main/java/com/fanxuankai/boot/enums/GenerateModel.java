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

    public boolean isGenerateDataOnly() {
        return generateDataOnly;
    }

    public void setGenerateDataOnly(boolean generateDataOnly) {
        this.generateDataOnly = generateDataOnly;
    }
}
