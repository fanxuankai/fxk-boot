package com.fanxuankai.boot.springfox;

/**
 * @author fanxuankai
 */
public class ApiInfoConfiguration {
    /**
     * 应用名
     */
    private String name;

    /**
     * 版本信息
     */
    private String version;

    /**
     * 描述信息
     */
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
