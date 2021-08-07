package com.fanxuankai.boot.generator.utils;

import cn.hutool.core.util.StrUtil;
import com.fanxuankai.boot.generator.autoconfigure.CodeGeneratorProperties;
import com.mysql.cj.MysqlType;

import java.util.Objects;

/**
 * @author fanxuankai
 */
public class JavaTypeUtils {
    private static final String NULL_CLASS_NAME = "[B";

    public static String getJavaType(CodeGeneratorProperties properties, String mysqlTypeName, boolean simpleName) {
        String javaType = properties.getColumnTypeMapping().get(mysqlTypeName);
        if (StrUtil.isNotBlank(javaType)) {
            return javaType;
        }
        MysqlType mysqlType = MysqlType.getByName(mysqlTypeName);
        String className = mysqlType.getClassName();
        if (Objects.equals(className, NULL_CLASS_NAME)) {
            throw new RuntimeException("无法识别 MySQL 字段类型: " + mysqlTypeName);
        }
        if (simpleName) {
            javaType = StrUtil.subAfter(className, ".", true);
        }
        return javaType;
    }
}
