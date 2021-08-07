package com.fanxuankai.boot.mybatis.plus.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Date;

/**
 * @author fanxuankai
 */
@ConfigurationProperties(prefix = "fxk.mybatis-plus")
public class MybatisPlusProperties {
    @NestedConfigurationProperty
    private PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
    @NestedConfigurationProperty
    private MetaObject metaObject = new MetaObject();

    public PaginationInterceptor getPaginationInterceptor() {
        return paginationInterceptor;
    }

    public void setPaginationInterceptor(PaginationInterceptor paginationInterceptor) {
        this.paginationInterceptor = paginationInterceptor;
    }

    public MetaObject getMetaObject() {
        return metaObject;
    }

    public void setMetaObject(MetaObject metaObject) {
        this.metaObject = metaObject;
    }

    public static class PaginationInterceptor {
        private Long limit;

        public Long getLimit() {
            return limit;
        }

        public void setLimit(Long limit) {
            this.limit = limit;
        }
    }

    public static class MetaObject {
        private String createDateFieldName = "createDate";
        private String createDateFieldType = Date.class.getName();
        private String modifiedDateFieldName = "lastModifiedDate";
        private String modifiedDateFieldType = Date.class.getName();
        private String deletedFieldName = "deleted";
        private String deletedFieldType = Integer.class.getName();

        public String getCreateDateFieldName() {
            return createDateFieldName;
        }

        public void setCreateDateFieldName(String createDateFieldName) {
            this.createDateFieldName = createDateFieldName;
        }

        public String getCreateDateFieldType() {
            return createDateFieldType;
        }

        public void setCreateDateFieldType(String createDateFieldType) {
            this.createDateFieldType = createDateFieldType;
        }

        public String getModifiedDateFieldName() {
            return modifiedDateFieldName;
        }

        public void setModifiedDateFieldName(String modifiedDateFieldName) {
            this.modifiedDateFieldName = modifiedDateFieldName;
        }

        public String getModifiedDateFieldType() {
            return modifiedDateFieldType;
        }

        public void setModifiedDateFieldType(String modifiedDateFieldType) {
            this.modifiedDateFieldType = modifiedDateFieldType;
        }

        public String getDeletedFieldName() {
            return deletedFieldName;
        }

        public void setDeletedFieldName(String deletedFieldName) {
            this.deletedFieldName = deletedFieldName;
        }

        public String getDeletedFieldType() {
            return deletedFieldType;
        }

        public void setDeletedFieldType(String deletedFieldType) {
            this.deletedFieldType = deletedFieldType;
        }
    }
}
