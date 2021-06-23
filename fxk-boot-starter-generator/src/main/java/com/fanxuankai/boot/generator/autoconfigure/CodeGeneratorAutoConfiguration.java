package com.fanxuankai.boot.generator.autoconfigure;

import com.fanxuankai.boot.generator.CodeGeneratorWorker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author fanxuankai
 */
@EnableConfigurationProperties({CodeGeneratorProperties.class})
public class CodeGeneratorAutoConfiguration {
    @Configuration
    @ConditionalOnProperty(prefix = CodeGeneratorProperties.PREFIX, name = "enabled", havingValue = "true")
    @ComponentScan(basePackageClasses = {CodeGeneratorWorker.class})
    public static class EnableCodeGeneratorConfiguration {
    }
}