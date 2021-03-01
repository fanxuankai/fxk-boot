package com.fanxuankai.boot.mybatis.plus.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询配置
 *
 * @author fanxuankai
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {

    /**
     * 属性名
     *
     * @return /
     */
    String field() default "";

    /**
     * 查询方式
     *
     * @return /
     */
    Type type() default Type.EQ;

    /**
     * 多字段模糊查询
     *
     * @return 以逗号隔开
     */
    String fuzzy() default "";

    enum Type {
        /**
         * ==
         */
        EQ,

        /**
         * >=
         */
        GE,

        /**
         * <=
         */
        LE,

        /**
         * like(%*%)
         */
        LIKE,

        /**
         * not like(%*%)
         */
        NOT_LIKE,

        /**
         * like(%*)
         */
        LIKE_LEFT,

        /**
         * like(*%)
         */
        LIKE_RIGHT,

        /**
         * >
         */
        GT,

        /**
         * <
         */
        LT,

        /**
         * in
         */
        IN,

        /**
         * not in
         */
        NOT_IN,

        /**
         * <>
         */
        NE,

        /**
         * between
         */
        BETWEEN,

        /**
         * not between
         */
        NOT_BETWEEN,

        /**
         * not null
         */
        NOT_NULL,

        /**
         * is null
         */
        IS_NULL,

        /**
         * order by asc
         */
        ORDER_BY_ASC,

        /**
         * order by desc
         */
        ORDER_BY_DESC,
    }

}

