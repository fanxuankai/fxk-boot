package com.fanxuankai.boot.enums.files;

import com.fanxuankai.commons.util.EnumProtocol;

/**
 * 是否删除
 *
 * @author fanxuankai
 */
public enum Deleted implements EnumProtocol {
    /**
     * 未删除
     */
    NO(10, "未删除"),
    /**
     * 已删除
     */
    YES(17, "已删除"),
    ;
    private final int code;
    private final String value;

    Deleted(int code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}