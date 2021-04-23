package com.fanxuankai.boot.generator.config;

import com.fanxuankai.boot.generator.CodeGenerator;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author fanxuankai
 */
@EnableConfigurationProperties({CodeGeneratorProperties.class})
@ComponentScan(basePackageClasses = {CodeGenerator.class})
public class CodeGeneratorAutoconfigure {
}