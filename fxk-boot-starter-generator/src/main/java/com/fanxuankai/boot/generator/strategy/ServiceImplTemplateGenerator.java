package com.fanxuankai.boot.generator.strategy;

import com.fanxuankai.boot.generator.config.CodeGeneratorProperties;
import com.fanxuankai.boot.generator.model.ColumnInfo;
import com.fanxuankai.boot.generator.model.GenConfig;
import com.fanxuankai.boot.generator.strategy.annotation.TemplateFileAnnotation;
import com.fanxuankai.boot.generator.strategy.enums.TemplateFile;
import com.fanxuankai.boot.generator.strategy.model.ColumnData;
import com.fanxuankai.boot.generator.strategy.model.ServiceImplTemplateData;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author fanxuankai
 */
@Service
@TemplateFileAnnotation(TemplateFile.SERVICE_IMPL)
public class ServiceImplTemplateGenerator extends AbstractTemplateGenerator<ServiceImplTemplateData> {

    @Override
    protected String getModulePath(GenConfig genConfig) {
        return genConfig.getWebPath();
    }

    @Override
    protected String getRole() {
        return "service" + File.separator + "impl";
    }

    @Override
    protected String getSuffix() {
        return "ServiceImpl";
    }

    @Override
    protected ServiceImplTemplateData getTemplateData(GenConfig genConfig, CodeGeneratorProperties properties) {
        ServiceImplTemplateData data = super.getTemplateData(genConfig, properties);
        data.getColumns().stream()
                .filter(ColumnInfo::isPrimaryKey)
                .findFirst()
                .map(ColumnData::getCapitalFieldName)
                .ifPresent(data::setPkCapitalColName);
        return data;
    }
}
