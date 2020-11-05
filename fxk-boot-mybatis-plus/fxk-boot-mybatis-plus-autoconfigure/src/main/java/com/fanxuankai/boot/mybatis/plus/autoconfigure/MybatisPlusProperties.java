package com.fanxuankai.boot.mybatis.plus.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Date;

/**
 * @author fanxuankai
 */
@Data
@ConfigurationProperties(prefix = "mybatis-plus")
public class MybatisPlusProperties {
    @NestedConfigurationProperty
    private PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
    @NestedConfigurationProperty
    private MetaObject metaObject = new MetaObject();

    @Data
    public static class PaginationInterceptor {
        private Long limit;
    }

    @Data
    public static class MetaObject {
        private String createDateFieldName = "createDate";
        private String createDateFieldType = Date.class.getName();
        private String modifiedDateFieldName = "lastModifiedDate";
        private String modifiedDateFieldType = Date.class.getName();
        private String deletedFieldName = "deleted";
        private String deletedFieldType = Integer.class.getName();
    }
}
