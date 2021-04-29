package com.fanxuankai.boot.generator.strategy;

import com.fanxuankai.boot.generator.model.GenConfig;
import com.fanxuankai.boot.generator.autoconfigure.CodeGeneratorProperties;

import java.io.IOException;

/**
 * 模板代码生成器
 *
 * @author fanxuankai
 */
public interface TemplateGenerator {
    /**
     * 生成代码
     *
     * @param genConfig  生成配置
     * @param properties 配置文件
     * @throws IOException 模板文件不存在时抛出异常
     */
    void generate(GenConfig genConfig, CodeGeneratorProperties properties) throws IOException;
}
