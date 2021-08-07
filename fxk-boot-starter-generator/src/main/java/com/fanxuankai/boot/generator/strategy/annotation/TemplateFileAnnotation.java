package com.fanxuankai.boot.generator.strategy.annotation;

import com.fanxuankai.boot.generator.strategy.enums.TemplateFile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author fanxuankai
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TemplateFileAnnotation {
    /**
     * 模板文件
     *
     * @return /
     */
    TemplateFile value();
}
