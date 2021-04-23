package com.fanxuankai.boot.enums;

import com.fanxuankai.boot.enums.domain.Enum;
import com.fanxuankai.boot.enums.domain.EnumType;

import java.util.List;

/**
 * 枚举 VO
 *
 * @author fanxuankai
 */
public class EnumVO {
    private EnumType enumType;
    private List<Enum> enumList;

    public EnumType getEnumType() {
        return enumType;
    }

    public void setEnumType(EnumType enumType) {
        this.enumType = enumType;
    }

    public List<Enum> getEnumList() {
        return enumList;
    }

    public void setEnumList(List<Enum> enumList) {
        this.enumList = enumList;
    }

    @Override
    public String toString() {
        return "EnumVO{" +
                "enumType=" + enumType +
                ", enumList=" + enumList +
                '}';
    }
}
