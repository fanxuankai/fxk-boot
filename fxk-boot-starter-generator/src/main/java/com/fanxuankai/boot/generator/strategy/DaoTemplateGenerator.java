package com.fanxuankai.boot.generator.strategy;

import com.fanxuankai.boot.generator.config.CodeGeneratorProperties;
import com.fanxuankai.boot.generator.model.ColumnInfo;
import com.fanxuankai.boot.generator.model.GenConfig;
import com.fanxuankai.boot.generator.strategy.annotation.TemplateFileAnnotation;
import com.fanxuankai.boot.generator.strategy.enums.TemplateFile;
import com.fanxuankai.boot.generator.strategy.model.DaoTemplateData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
@Service
@TemplateFileAnnotation(TemplateFile.DAO)
public class DaoTemplateGenerator extends AbstractTemplateGenerator<DaoTemplateData> {

    @Override
    protected List<ColumnInfo> filterColumnInfos(List<ColumnInfo> columnInfos) {
        return columnInfos.stream()
                .filter(ColumnInfo::isUnique)
                .collect(Collectors.toList());
    }

    @Override
    protected String getModulePath(GenConfig genConfig) {
        return genConfig.getWebPath();
    }

    @Override
    protected String getRole() {
        return "dao";
    }

    @Override
    protected String getSuffix() {
        return "Dao";
    }

    @Override
    protected DaoTemplateData getTemplateData(GenConfig genConfig, CodeGeneratorProperties properties) {
        DaoTemplateData data = super.getTemplateData(genConfig, properties);
        data.setHasUnique(data.getColumns().stream().anyMatch(ColumnInfo::isUnique));
        return data;
    }
}
