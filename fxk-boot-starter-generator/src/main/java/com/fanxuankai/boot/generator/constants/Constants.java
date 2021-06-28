package com.fanxuankai.boot.generator.constants;

import cn.hutool.core.map.MapUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author fanxuankai
 */
public class Constants {
    public static final String TIMESTAMP = "Timestamp";
    public static final String DATE = "Date";
    public static final String LONG = "Long";
    public static final String BIGDECIMAL = "BigDecimal";
    public static final String PK = "PRI";
    public static final String UNI = "UNI";
    public static final String AUTO_INCREMENT = "auto_increment";

    public static final String NOT_NULL = "NOT_NULL";
    public static final String IS_NULL = "IS_NULL";
    public static final String ORDER_BY_ASC = "ORDER_BY_ASC";
    public static final String ORDER_BY_DESC = "ORDER_BY_DESC";
    public static final List<String> BE_BOOLEAN_COLUMN_TYPE_QUERY_TYPES =
            Arrays.asList(NOT_NULL, IS_NULL, ORDER_BY_ASC, ORDER_BY_DESC);

    public static final Map<String, String> COLUMN_TYPE_MAPPING = MapUtil.<String, String>builder()
            .put("bigint", "Long")
            .put("bit", "Boolean")
            .put("char", "String")
            .put("date", "Date")
            .put("datetime", "Date")
            .put("decimal", "BigDecimal")
            .put("double", "Double")
            .put("float", "Float")
            .put("int", "Integer")
            .put("integer", "Integer")
            .put("longtext", "String")
            .put("mediumint", "Integer")
            .put("mediumtext", "String")
            .put("smallint", "Integer")
            .put("text", "String")
            .put("timestamp", "Timestamp")
            .put("tinyint", "Integer")
            .put("tinytext", "String")
            .put("varchar", "String")
            .build();

    public static final Map<String, String> AUTO_FILL = MapUtil.<String, String>builder()
            .put("create_user_id", "INSERT")
            .put("gmt_create", "INSERT")
            .put("modified_user_id", "UPDATE")
            .put("gmt_modified", "UPDATE")
            .put("deleted", "INSERT")
            .build();

    public static final List<String> FORM_EXCLUDE_COLUMNS = Arrays.asList("create_user_id", "gmt_create",
            "modified_user_id", "gmt_modified", "deleted");

    public static final List<String> LIST_EXCLUDE_COLUMNS = Collections.singletonList("deleted");

    public static final List<String> INHERITED_COLUMNS = Arrays.asList("id", "create_user_id", "gmt_create",
            "modified_user_id", "gmt_modified", "deleted");
}
