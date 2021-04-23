package com.fanxuankai.boot.generator.constants;

import java.util.Arrays;
import java.util.List;

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

    // Query Type

    public static final String EQ = "EQ";
    public static final String GE = "GE";
    public static final String LE = "LE";
    public static final String LIKE = "LIKE";
    public static final String NOT_LIKE = "NOT_LIKE";
    public static final String LIKE_LEFT = "LIKE_LEFT";
    public static final String LIKE_RIGHT = "LIKE_RIGHT";
    public static final String GT = "GT";
    public static final String LT = "LT";
    public static final String IN = "IN";
    public static final String NOT_IN = "NOT_IN";
    public static final String NE = "NE";
    public static final String BETWEEN = "BETWEEN";
    public static final String NOT_BETWEEN = "NOT_BETWEEN";
    public static final String NOT_NULL = "NOT_NULL";
    public static final String IS_NULL = "IS_NULL";
    public static final String ORDER_BY_ASC = "ORDER_BY_ASC";
    public static final String ORDER_BY_DESC = "ORDER_BY_DESC";
    public static final List<String> BE_BOOLEAN_COLUMN_TYPE_QUERY_TYPES =
            Arrays.asList(NOT_NULL, IS_NULL, ORDER_BY_ASC, ORDER_BY_DESC);
}
