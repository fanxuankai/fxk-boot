package com.fanxuankai.boot.generator.autoconfigure;

import com.fanxuankai.boot.generator.service.GeneratorService;
import com.fanxuankai.boot.generator.strategy.TemplateGenerator;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author fanxuankai
 */
@EnableConfigurationProperties({CodeGeneratorProperties.class})
@ComponentScan(basePackageClasses = {GeneratorService.class, TemplateGenerator.class})
@Import({CodeGeneratorWorker.class})
public class CodeGeneratorAutoConfiguration {
}