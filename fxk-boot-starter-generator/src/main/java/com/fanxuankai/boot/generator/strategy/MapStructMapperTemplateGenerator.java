package com.fanxuankai.boot.generator.strategy;

import com.fanxuankai.boot.generator.model.GenConfig;
import com.fanxuankai.boot.generator.strategy.annotation.TemplateFileAnnotation;
import com.fanxuankai.boot.generator.strategy.enums.TemplateFile;
import com.fanxuankai.boot.generator.strategy.model.TemplateData;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author fanxuankai
 */
@Service
@TemplateFileAnnotation(TemplateFile.MAP_STRUCT_MAPPER)
public class MapStructMapperTemplateGenerator extends AbstractTemplateGenerator<TemplateData> {

    @Override
    protected String getModulePath(GenConfig genConfig) {
        return genConfig.getWebPath();
    }

    @Override
    protected String getRole() {
        return "service" + File.separator + "mapstruct";
    }

    @Override
    protected String getSuffix() {
        return "Converter";
    }

}
