package com.fanxuankai.boot.generator.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author fanxuankai
 */
@ConfigurationProperties(prefix = "code-generator")
public class CodeGeneratorProperties {
    private String schema;
    private String tables;
    private String author;
    private String prefix;
    private String projectDir;
    private String apiPath;
    private String webPath;
    private String serviceName;
    private String packageName;
    private boolean cover;
    private Map<String, String> autoFill = Collections.emptyMap();
    private List<String> formExcludeColumns = Collections.emptyList();
    private List<String> listExcludeColumns = Collections.emptyList();
    private List<String> inheritedColumns = Collections.emptyList();
    private Map<String, String> columnTypeMapping = Collections.emptyMap();

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTables() {
        return tables;
    }

    public void setTables(String tables) {
        this.tables = tables;
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

    public Map<String, String> getAutoFill() {
        return autoFill;
    }

    public void setAutoFill(Map<String, String> autoFill) {
        this.autoFill = autoFill;
    }

    public List<String> getFormExcludeColumns() {
        return formExcludeColumns;
    }

    public void setFormExcludeColumns(List<String> formExcludeColumns) {
        this.formExcludeColumns = formExcludeColumns;
    }

    public List<String> getListExcludeColumns() {
        return listExcludeColumns;
    }

    public void setListExcludeColumns(List<String> listExcludeColumns) {
        this.listExcludeColumns = listExcludeColumns;
    }

    public List<String> getInheritedColumns() {
        return inheritedColumns;
    }

    public void setInheritedColumns(List<String> inheritedColumns) {
        this.inheritedColumns = inheritedColumns;
    }

    public Map<String, String> getColumnTypeMapping() {
        return columnTypeMapping;
    }

    public void setColumnTypeMapping(Map<String, String> columnTypeMapping) {
        this.columnTypeMapping = columnTypeMapping;
    }
}
