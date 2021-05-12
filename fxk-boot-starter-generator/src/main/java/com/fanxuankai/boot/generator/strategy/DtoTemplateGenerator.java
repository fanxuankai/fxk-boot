package com.fanxuankai.boot.generator.strategy;

import com.fanxuankai.boot.generator.autoconfigure.CodeGeneratorProperties;
import com.fanxuankai.boot.generator.model.ColumnInfo;
import com.fanxuankai.boot.generator.model.GenConfig;
import com.fanxuankai.boot.generator.strategy.annotation.TemplateFileAnnotation;
import com.fanxuankai.boot.generator.strategy.enums.TemplateFile;
import com.fanxuankai.boot.generator.strategy.model.DtoTemplateData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
@Service
@TemplateFileAnnotation(TemplateFile.DTO)
public class DtoTemplateGenerator extends AbstractTemplateGenerator<DtoTemplateData> {

    @Override
    protected List<ColumnInfo> filterColumnInfos(List<ColumnInfo> columnInfos) {
        return columnInfos.stream()
                .filter(ColumnInfo::isFormShow)
                // dto 不需要 id
                .filter(o -> !o.isPrimaryKey())
                .collect(Collectors.toList());
    }

    @Override
    protected String getModulePath(GenConfig genConfig) {
        return genConfig.getApiPath();
    }

    @Override
    protected String getRole() {
        return "dto";
    }

    @Override
    protected String getSuffix() {
        return "Dto";
    }

    @Override
    protected DtoTemplateData getTemplateData(GenConfig genConfig, CodeGeneratorProperties properties) {
        DtoTemplateData data = super.getTemplateData(genConfig, properties);
        data.setHasNotNull(data.getColumns().stream().anyMatch(ColumnInfo::isNotNull));
        return data;
    }
}
