package com.fanxuankai.boot.generator.strategy;

import com.fanxuankai.boot.generator.model.GenConfig;
import com.fanxuankai.boot.generator.strategy.annotation.TemplateFileAnnotation;
import com.fanxuankai.boot.generator.strategy.enums.TemplateFile;
import com.fanxuankai.boot.generator.strategy.model.TemplateData;
import org.springframework.stereotype.Service;

/**
 * @author fanxuankai
 */
@Service
@TemplateFileAnnotation(TemplateFile.SERVICE)
public class ServiceTemplateGenerator extends AbstractTemplateGenerator<TemplateData> {

    @Override
    protected String getModulePath(GenConfig genConfig) {
        return genConfig.getWebPath();
    }

    @Override
    protected String getRole() {
        return "service";
    }

    @Override
    protected String getSuffix() {
        return "Service";
    }

}
