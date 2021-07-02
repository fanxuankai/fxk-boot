package com.fanxuankai.boot.generator.autoconfigure;

import com.fanxuankai.boot.generator.constants.Constants;
import com.fanxuankai.boot.generator.strategy.enums.TemplateFile;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author fanxuankai
 */
@ConfigurationProperties(prefix = CodeGeneratorProperties.PREFIX)
public class CodeGeneratorProperties {
    /**
     * 配置前缀
     */
    public static final String PREFIX = "fxk.code-generator";
    /**
     * 开关
     */
    private boolean enabled;
    /**
     * 数据库
     */
    private String schema;
    /**
     * 表名列表
     */
    private String tables;
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
    private boolean cover = true;
    /**
     * 自动填值 { 列名: INSERT|UPDATE|INSERT_UPDATE }
     */
    private Map<String, String> autoFill = Constants.AUTO_FILL;
    /**
     * dto 不显示的字段
     */
    private List<String> formExcludeColumns = Constants.FORM_EXCLUDE_COLUMNS;
    /**
     * vo 不显示的字段
     */
    private List<String> listExcludeColumns = Constants.LIST_EXCLUDE_COLUMNS;
    /**
     * 父类字段
     */
    private List<String> inheritedColumns = Constants.INHERITED_COLUMNS;
    /**
     * 字段类型映射
     */
    private Map<String, String> columnTypeMapping = Constants.COLUMN_TYPE_MAPPING;
    /**
     * 要生成的类文件
     */
    private List<TemplateFile> filesToCreate = Collections.emptyList();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

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

    public List<TemplateFile> getFilesToCreate() {
        return filesToCreate;
    }

    public void setFilesToCreate(List<TemplateFile> filesToCreate) {
        this.filesToCreate = filesToCreate;
    }
}
