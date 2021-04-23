package com.fanxuankai.boot.springfox;

/**
 * @author fanxuankai
 */
public class ApisConfiguration {
    /**
     * 基础包
     */
    private String basePackage;
    /**
     * 类注解
     */
    private String classAnnotation;
    /**
     * 方法注解
     */
    private String methodAnnotation;

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getClassAnnotation() {
        return classAnnotation;
    }

    public void setClassAnnotation(String classAnnotation) {
        this.classAnnotation = classAnnotation;
    }

    public String getMethodAnnotation() {
        return methodAnnotation;
    }

    public void setMethodAnnotation(String methodAnnotation) {
        this.methodAnnotation = methodAnnotation;
    }
}
