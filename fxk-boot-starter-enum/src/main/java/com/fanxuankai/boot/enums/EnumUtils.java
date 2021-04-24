package com.fanxuankai.boot.enums;

import org.springframework.lang.Nullable;

import java.util.Objects;
import java.util.Optional;

/**
 * 枚举工具类
 *
 * @author fanxuankai
 */
public class EnumUtils {
    /**
     * 查枚举
     *
     * @param enumClass 枚举类
     * @param code      枚举代码
     * @param <E>       枚举泛型
     * @return 可能为 Optional.empty()
     */
    public static <E extends EnumProtocol> Optional<E> find(Class<E> enumClass, @Nullable Integer code) {
        for (E e : enumClass.getEnumConstants()) {
            if (Objects.equals(e.getCode(), code)) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

    /**
     * 查枚举
     *
     * @param enumClass 枚举类
     * @param value     枚举值
     * @param <E>       枚举泛型
     * @return 可能为 Optional.empty()
     */
    public static <E extends EnumProtocol> Optional<E> findByValue(Class<E> enumClass, @Nullable String value) {
        for (E e : enumClass.getEnumConstants()) {
            if (Objects.equals(e.getValue(), value)) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

    /**
     * 查枚举
     *
     * @param enumClass 枚举类
     * @param name      枚举名称
     * @param <E>       枚举泛型
     * @return 可能为 Optional.empty()
     */
    public static <E extends Enum<?>> Optional<E> findByName(Class<E> enumClass, @Nullable String name) {
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
     * @param enumClass 枚举类
     * @param code      枚举代码
     * @param <E>       枚举泛型
     * @return 可能为 null
     */
    public static <E extends EnumProtocol> E get(Class<E> enumClass, @Nullable Integer code) {
        return find(enumClass, code).orElse(null);
    }

    /**
     * 查枚举值
     *
     * @param enumClass 枚举类
     * @param code      枚举代码
     * @param <E>       枚举泛型
     * @return 可能为 null
     */
    public static <E extends EnumProtocol> String getValue(Class<E> enumClass, @Nullable Integer code) {
        return find(enumClass, code).map(EnumProtocol::getValue).orElse(null);
    }

    /**
     * 查枚举 code
     *
     * @param enumClass 枚举类
     * @param value     枚举值
     * @param <E>       枚举泛型
     * @return 可能为 null
     */
    public static <E extends EnumProtocol> Integer getCode(Class<E> enumClass, @Nullable String value) {
        return findByValue(enumClass, value).map(EnumProtocol::getCode).orElse(null);
    }

    /**
     * 查枚举
     *
     * @param enumClass 枚举类
     * @param name      枚举名称
     * @param <E>       枚举泛型
     * @return 可能为 null
     */
    public static <E extends Enum<?>> E get(Class<E> enumClass, @Nullable String name) {
        return findByName(enumClass, name).orElse(null);
    }

    /**
     * 判断是否相等
     *
     * @param anEnum 枚举
     * @param code   代码
     * @param <E>    枚举泛型
     * @return code 相同返回 true
     */
    public static <E extends EnumProtocol> boolean equals(E anEnum, Integer code) {
        return Objects.equals(anEnum.getCode(), code);
    }
}