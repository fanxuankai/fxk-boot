package com.fanxuankai.boot.springfox.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.List;

/**
 * @author fanxuankai
 */
@ConfigurationProperties(SwaggerProperties.PREFIX)
public class SwaggerProperties {

    public static final String PREFIX = "swagger";

    @NestedConfigurationProperty
    private Docket docket;

    public Docket getDocket() {
        return docket;
    }

    public void setDocket(Docket docket) {
        this.docket = docket;
    }

    public static class ApiInfo {
        /**
         * 应用名
         */
        private String name;

        /**
         * 版本信息
         */
        private String version;

        /**
         * 描述信息
         */
        private String description;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class Apis {
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

    public static class Docket {
        /**
         * 开关
         */
        private Boolean enabled;

        /**
         * 项目信息
         */
        @NestedConfigurationProperty
        private ApiInfo apiInfo;

        /**
         * 地址
         */
        private String host;

        /**
         * api 配置
         */
        @NestedConfigurationProperty
        private Apis apis;

        /**
         * 自定义 header key
         */
        private List<String> headers;

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public ApiInfo getApiInfo() {
            return apiInfo;
        }

        public void setApiInfo(ApiInfo apiInfo) {
            this.apiInfo = apiInfo;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Apis getApis() {
            return apis;
        }

        public void setApis(Apis apis) {
            this.apis = apis;
        }

        public List<String> getHeaders() {
            return headers;
        }

        public void setHeaders(List<String> headers) {
            this.headers = headers;
        }
    }
}
