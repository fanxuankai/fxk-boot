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
@TemplateFileAnnotation(TemplateFile.API_IMPL)
public class ApiImplTemplateGenerator extends AbstractTemplateGenerator<TemplateData> {

    @Override
    protected String getModulePath(GenConfig genConfig) {
        return genConfig.getWebPath();
    }

    @Override
    protected String getRole() {
        return "api";
    }

    @Override
    protected String getSuffix() {
        return "ApiImpl";
    }

}
