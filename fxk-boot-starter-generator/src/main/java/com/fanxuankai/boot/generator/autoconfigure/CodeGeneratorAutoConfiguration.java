package com.fanxuankai.boot.generator.autoconfigure;

import com.fanxuankai.boot.generator.CodeGeneratorWorker;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author fanxuankai
 */
@EnableConfigurationProperties({CodeGeneratorProperties.class})
@ComponentScan(basePackageClasses = {CodeGeneratorWorker.class})
public class CodeGeneratorAutoConfiguration {
}