package com.fanxuankai.boot.enums;

import org.springframework.lang.Nullable;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

/**
 * 枚举管理类
 *
 * @author fanxuankai
 */
public class EnumManager {

    private static final String CODE_FIELD = "code";
    private static final String VALUE_FIELD = "value";

    /**
     * 查枚举
     *
     * @param enumClass 枚举类型
     * @param code      枚举代码
     * @param <E>       枚举泛型
     * @return 可能为 Optional.empty()
     */
    public static <E extends Enum<?>> Optional<E> find(Class<E> enumClass, @Nullable Integer code) {
        return findByField(enumClass, CODE_FIELD, code);
    }

    /**
     * 查枚举
     *
     * @param enumClass 枚举类型
     * @param value     枚举值
     * @param <E>       枚举泛型
     * @return 可能为 Optional.empty()
     */
    public static <E extends Enum<?>> Optional<E> findByValue(Class<E> enumClass, @Nullable String value) {
        return findByField(enumClass, VALUE_FIELD, value);
    }

    /**
     * 查枚举
     *
     * @param enumClass 枚举类型
     * @param name      枚举名称
     * @param <E>       枚举泛型
     * @return 可能为 Optional.empty()
     */
    public static <E extends Enum<?>> Optional<E> find(Class<E> enumClass, @Nullable String name) {
        for (E enumConstant : enumClass.getEnumConstants()) {
            if (Objects.equals(enumConstant.name(), name)) {
                return Optional.of(enumConstant);
            }
        }
        return Optional.empty();
    }

    /**
     * 查枚举
     *
     * @param enumClass 枚举类型
     * @param code      枚举代码
     * @param <E>       枚举泛型
     * @return 可能为 null
     */
    public static <E extends Enum<?>> E get(Class<E> enumClass, @Nullable Integer code) {
        return find(enumClass, code).orElse(null);
    }

    /**
     * 查枚举值
     *
     * @param enumClass 枚举类型
     * @param code      枚举代码
     * @param <E>       枚举泛型
     * @return 可能为 null
     */
    public static <E extends Enum<?>> String getValue(Class<E> enumClass, @Nullable Integer code) {
        return find(enumClass, code)
                .map(o -> {
                    try {
                        Field field = o.getClass().getDeclaredField(VALUE_FIELD);
                        field.setAccessible(true);
                        return field.get(o).toString();
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        return null;
                    }
                }).orElse(null);
    }

    /**
     * 查枚举 code
     *
     * @param enumClass 枚举类型
     * @param value     枚举值
     * @param <E>       枚举泛型
     * @return 可能为 null
     */
    public static <E extends Enum<?>> Integer getCode(Class<E> enumClass, @Nullable String value) {
        return findByValue(enumClass, value)
                .map(o -> {
                    try {
                        Field field = o.getClass().getDeclaredField(CODE_FIELD);
                        field.setAccessible(true);
                        return Integer.parseInt(field.get(o).toString());
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        return null;
                    }
                }).orElse(null);
    }

    /**
     * 查枚举
     *
     * @param enumClass 枚举类型
     * @param name      枚举名称
     * @param <E>       枚举泛型
     * @return 可能为 null
     */
    public static <E extends Enum<?>> E get(Class<E> enumClass, @Nullable String name) {
        return find(enumClass, name).orElse(null);
    }

    /**
     * 判断是否相等
     *
     * @param anEnum 枚举
     * @param code   代码
     * @param <E>    枚举泛型
     * @return code 相同返回 true
     */
    public static <E extends Enum<?>> boolean equals(E anEnum, @Nullable Integer code) {
        try {
            Field codeField = anEnum.getClass().getDeclaredField("code");
            codeField.setAccessible(true);
            return Objects.equals(codeField.get(anEnum), code);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {

        }
        return false;
    }

    private static <E extends Enum<?>> Optional<E> findByField(Class<E> enumClass, String field, @Nullable Object obj) {
        try {
            Field codeField = enumClass.getDeclaredField(field);
            codeField.setAccessible(true);
            for (E enumConstant : enumClass.getEnumConstants()) {
                if (Objects.equals(codeField.get(enumConstant), obj)) {
                    return Optional.of(enumConstant);
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException ignored) {

        }
        return Optional.empty();
    }

}