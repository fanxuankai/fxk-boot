package com.fanxuankai.boot.generator.strategy;

import com.fanxuankai.boot.generator.model.ColumnInfo;
import com.fanxuankai.boot.generator.model.GenConfig;
import com.fanxuankai.boot.generator.strategy.annotation.TemplateFileAnnotation;
import com.fanxuankai.boot.generator.strategy.enums.TemplateFile;
import com.fanxuankai.boot.generator.strategy.model.TemplateData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
@Service
@TemplateFileAnnotation(TemplateFile.VO)
public class VoTemplateGenerator extends AbstractTemplateGenerator<TemplateData> {

    @Override
    protected List<ColumnInfo> filterColumnInfos(List<ColumnInfo> columnInfos) {
        return columnInfos.stream()
                .filter(ColumnInfo::isListShow)
                .collect(Collectors.toList());
    }

    @Override
    protected String getModulePath(GenConfig genConfig) {
        return genConfig.getApiPath();
    }

    @Override
    protected String getRole() {
        return "vo";
    }

    @Override
    protected String getSuffix() {
        return "Vo";
    }

}
