package com.fanxuankai.boot.generator.strategy;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.fanxuankai.boot.generator.config.CodeGeneratorProperties;
import com.fanxuankai.boot.generator.model.ColumnInfo;
import com.fanxuankai.boot.generator.model.GenConfig;
import com.fanxuankai.boot.generator.strategy.annotation.TemplateFileAnnotation;
import com.fanxuankai.boot.generator.strategy.model.ColumnData;
import com.fanxuankai.boot.generator.strategy.model.TemplateData;
import com.fanxuankai.boot.generator.utils.StringUtils;
import com.fanxuankai.boot.generator.constants.Constants;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * 抽象模板代码生成器
 *
 * @param <D> 模板数据泛型
 * @author fanxuankai
 */
public abstract class AbstractTemplateGenerator<D extends TemplateData> implements TemplateGenerator {

    private final TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template",
            TemplateConfig.ResourceMode.CLASSPATH));

    /**
     * 过滤
     *
     * @param columnInfos /
     * @return /
     */
    protected List<ColumnInfo> filterColumnInfos(List<ColumnInfo> columnInfos) {
        return columnInfos;
    }

    /**
     * 获取模块相对路径
     *
     * @param genConfig /
     * @return /
     */
    abstract protected String getModulePath(GenConfig genConfig);

    /**
     * 获取代码所属子包名,如:controller
     *
     * @return /
     */
    abstract protected String getRole();

    /**
     * 获取类后缀名
     *
     * @return /
     */
    abstract protected String getSuffix();

    /**
     * 获取模板环境数据
     *
     * @param genConfig  /
     * @param properties /
     * @return /
     */
    protected D getTemplateData(GenConfig genConfig, CodeGeneratorProperties properties) {
        TemplateData data = new TemplateData();
        BeanUtil.copyProperties(genConfig, data);
        data.setClassName(getClassName(genConfig));
        data.setChangeClassName(getChangeClassName(genConfig));
        for (ColumnInfo columnInfo : genConfig.getColumnInfos()) {
            if (columnInfo.isPrimaryKey()) {
                data.setPkCapitalFieldType(properties.getColumnTypeMapping().get(columnInfo.getColumnType()));
                if (StrUtil.isBlank(columnInfo.getFieldName())) {
                    // 列名转驼峰
                    data.setPkFieldName(StringUtils.toCamelCase(columnInfo.getColumnName()));
                } else {
                    data.setPkFieldName(columnInfo.getFieldName());
                }
            }
        }
        List<ColumnData> columns = ListUtil.list(false);
        for (ColumnInfo columnInfo : filterColumnInfos(genConfig.getColumnInfos())) {
            ColumnData columnData = new ColumnData();
            BeanUtil.copyProperties(columnInfo, columnData);
            if (StrUtil.isBlank(columnData.getFieldName())) {
                // 列名转驼峰
                columnData.setFieldName(StringUtils.toCamelCase(columnInfo.getColumnName()));
            }
            // 列名转驼峰且首字母大写
            columnData.setCapitalFieldName(StringUtils.toCapitalizeCamelCase(columnInfo.getColumnName()));
            String fieldType = properties.getColumnTypeMapping().get(columnInfo.getColumnType());
            columnData.setFieldType(fieldType);
            columns.add(columnData);
            if (Constants.TIMESTAMP.equals(fieldType)) {
                data.setHasTimestamp(true);
            }
            if (Constants.DATE.equals(fieldType)) {
                data.setHasDate(true);
            }
            if (Constants.LONG.equals(fieldType)) {
                data.setHasLong(true);
            }
            if (Constants.BIGDECIMAL.equals(fieldType)) {
                data.setHasBigDecimal(true);
            }
        }
        data.setColumns(columns);
        return convertToActualType(data);
    }

    @Override
    public void generate(GenConfig genConfig, CodeGeneratorProperties properties) throws IOException {
        String filePath = getFilePath(genConfig);
        // 如果非覆盖生成
        if (!genConfig.isCover() && FileUtil.exist(filePath)) {
            return;
        }
        String resource = getClass().getAnnotation(TemplateFileAnnotation.class).value().getName();
        Template template = engine.getTemplate(resource + ".ftl");
        Map<?, ?> bindingMap = MapUtil.newHashMap();
        BeanUtil.copyProperties(getTemplateData(genConfig, properties), bindingMap);
        // 生成代码
        genFile(filePath, template, bindingMap);
    }

    private String getClassName(GenConfig genConfig) {
        String s = StringUtils.toCapitalizeCamelCase(genConfig.getTableName());
        if (StrUtil.isNotEmpty(genConfig.getPrefix())) {
            s = StringUtils.toCapitalizeCamelCase(StrUtil.removePrefix(genConfig.getTableName(),
                    genConfig.getPrefix()));
        }
        return s;
    }

    private String getChangeClassName(GenConfig genConfig) {
        String s = StringUtils.toCamelCase(genConfig.getTableName());
        if (StrUtil.isNotEmpty(genConfig.getPrefix())) {
            s = StringUtils.toCamelCase(StrUtil.removePrefix(genConfig.getTableName(), genConfig.getPrefix()));
        }
        return s;
    }

    /**
     * 获取文件路径
     *
     * @param genConfig 配置
     * @return /
     */
    private String getFilePath(GenConfig genConfig) {
        String modulePath = getModulePath(genConfig);
        String role = getRole();
        String suffix = getSuffix();
        String filePath =
                genConfig.getProjectDir() + beforeAddSeparator(modulePath) + File.separator + "src" + File.separator +
                        "main" + File.separator + "java";
        if (!ObjectUtils.isEmpty(genConfig.getPackageName())) {
            filePath += File.separator + genConfig.getPackageName().replace(".", File.separator);
        }
        String className = getClassName(genConfig) + (StrUtil.isNotBlank(suffix) ? suffix : "");
        filePath += beforeAddSeparator(role) + beforeAddSeparator(className) + ".java";
        return filePath;
    }

    private void genFile(String filePath, Template template, Map<?, ?> map) throws IOException {
        // 生成目标文件
        Writer writer = null;
        try {
            FileUtil.touch(filePath);
            writer = new FileWriter(filePath);
            template.render(map, writer);
        } finally {
            assert writer != null;
            writer.close();
        }
    }

    private String beforeAddSeparator(String str) {
        return StrUtil.isNotBlank(str) ? File.separator + str : "";
    }

    @SuppressWarnings("unchecked")
    private D convertToActualType(TemplateData data) {
        try {
            Class<D> dataClass = (Class<D>) TypeUtil.getTypeArgument(this.getClass(), 0);
            if (TemplateData.class.equals(dataClass)) {
                return (D) data;
            }
            D d = dataClass.getConstructor().newInstance();
            BeanUtil.copyProperties(data, d);
            return d;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
