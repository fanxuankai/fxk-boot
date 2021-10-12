package com.fanxuankai.boot.generator.strategy;

import cn.hutool.core.util.StrUtil;
import com.fanxuankai.boot.generator.autoconfigure.CodeGeneratorProperties;
import com.fanxuankai.boot.generator.model.ColumnInfo;
import com.fanxuankai.boot.generator.model.GenConfig;
import com.fanxuankai.boot.generator.strategy.annotation.TemplateFileAnnotation;
import com.fanxuankai.boot.generator.strategy.enums.TemplateFile;
import com.fanxuankai.boot.generator.strategy.model.EntityTemplateData;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
@Service
@TemplateFileAnnotation(TemplateFile.ENTITY)
public class EntityTemplateGenerator extends AbstractTemplateGenerator<EntityTemplateData> {

    private final CodeGeneratorProperties properties;

    public EntityTemplateGenerator(CodeGeneratorProperties properties) {
        this.properties = properties;
    }

    @Override
    protected List<ColumnInfo> filterColumnInfos(List<ColumnInfo> columnInfos) {
        if (properties.getInheritedColumns().isEmpty()) {
            return columnInfos;
        }
        return columnInfos.stream()
                .filter(o -> !properties.getInheritedColumns().contains(o.getColumnName()))
                .collect(Collectors.toList());
    }

    @Override
    protected String getModulePath(GenConfig genConfig) {
        return genConfig.getWebPath();
    }

    @Override
    protected String getRole() {
        return "model";
    }

    @Override
    protected String getSuffix() {
        return null;
    }

    @Override
    protected EntityTemplateData getTemplateData(GenConfig genConfig, CodeGeneratorProperties properties) {
        EntityTemplateData data = super.getTemplateData(genConfig, properties);
        data.setHasPrimaryKey(data.getColumns().stream().anyMatch(ColumnInfo::isPrimaryKey));
        data.setInherit(!CollectionUtils.isEmpty(properties.getInheritedColumns()));
        data.setFill(data.getColumns().stream().anyMatch(o -> StrUtil.isNotBlank(o.getFill())));
        return data;
    }
}
