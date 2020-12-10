package com.fanxuankai.boot.mybatis.plus.autoconfigure;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * @author fanxuankai
 */
public class DefaultMetaObjectHandler implements MetaObjectHandler {

    @Resource
    private MybatisPlusProperties mybatisPlusProperties;

    @Override
    public void insertFill(MetaObject metaObject) {
        MybatisPlusProperties.MetaObject propertiesMetaObject = mybatisPlusProperties.getMetaObject();
        String createDateFieldType = propertiesMetaObject.getCreateDateFieldType();
        String createDateFieldName = propertiesMetaObject.getCreateDateFieldName();
        if (Objects.equals(Date.class.getName(), createDateFieldType)) {
            strictInsertFill(metaObject, createDateFieldName, Date.class, new Date());
        } else if (Objects.equals(LocalDateTime.class.getName(), createDateFieldType)) {
            strictInsertFill(metaObject, createDateFieldName, LocalDateTime.class,
                    LocalDateTime.now());
        } else if (Objects.equals(LocalDate.class.getName(), createDateFieldType)) {
            strictInsertFill(metaObject, createDateFieldName, LocalDate.class, LocalDate.now());
        }
        String deletedFieldName = propertiesMetaObject.getDeletedFieldName();
        String deletedFieldType = propertiesMetaObject.getDeletedFieldType();
        if (Objects.equals(Integer.class.getName(), deletedFieldType)) {
            strictInsertFill(metaObject, deletedFieldName, Integer.class, 0);
        } else if (Objects.equals(Boolean.class.getName(), deletedFieldType)) {
            strictInsertFill(metaObject, deletedFieldName, Boolean.class, Boolean.FALSE);
        } else if (Objects.equals(Byte.class.getName(), deletedFieldType)) {
            strictInsertFill(metaObject, deletedFieldName, Byte.class, 0);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        MybatisPlusProperties.MetaObject propertiesMetaObject = mybatisPlusProperties.getMetaObject();
        String fieldType = propertiesMetaObject.getModifiedDateFieldType();
        String fieldName = propertiesMetaObject.getModifiedDateFieldName();
        if (Objects.equals(Date.class.getName(), fieldType)) {
            strictUpdateFill(metaObject, fieldName, Date.class, new Date());
        } else if (Objects.equals(LocalDateTime.class.getName(), fieldType)) {
            strictUpdateFill(metaObject, fieldName, LocalDateTime.class,
                    LocalDateTime.now());
        } else if (Objects.equals(LocalDate.class.getName(), fieldType)) {
            strictUpdateFill(metaObject, fieldName, LocalDate.class, LocalDate.now());
        }
    }

}