package com.fanxuankai.boot.generator.model;

import com.fanxuankai.boot.generator.strategy.enums.TemplateFile;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 代码生成配置
 *
 * @author fanxuankai
 */
public class GenConfig implements Serializable {
    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String comment;

    /**
     * 作者
     */
    private String author;

    /**
     * 表前缀
     */
    private String prefix;

    /**
     * 项目绝对路径
     */
    private String projectDir;

    /**
     * API 相对路径
     */
    private String apiPath;

    /**
     * WEB 相对路径
     */
    private String webPath;

    /**
     * 服务名
     */
    private String serviceName;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 是否覆盖
     */
    private boolean cover;

    /**
     * 要生成的类文件
     */
    private List<TemplateFile> filesToCreate = Collections.emptyList();

    /**
     * 列配置
     */
    private List<ColumnInfo> columnInfos;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getProjectDir() {
        return projectDir;
    }

    public void setProjectDir(String projectDir) {
        this.projectDir = projectDir;
    }

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public String getWebPath() {
        return webPath;
    }

    public void setWebPath(String webPath) {
        this.webPath = webPath;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isCover() {
        return cover;
    }

    public void setCover(boolean cover) {
        this.cover = cover;
    }

    public List<TemplateFile> getFilesToCreate() {
        return filesToCreate;
    }

    public void setFilesToCreate(List<TemplateFile> filesToCreate) {
        this.filesToCreate = filesToCreate;
    }

    public List<ColumnInfo> getColumnInfos() {
        return columnInfos;
    }

    public void setColumnInfos(List<ColumnInfo> columnInfos) {
        this.columnInfos = columnInfos;
    }
}
