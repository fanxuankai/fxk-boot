package com.fanxuankai.boot.enums;

import java.util.List;

/**
 * 枚举数据
 *
 * @author fanxuankai
 */
public class EnumDTO {

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

    /**
     * 枚举类型
     */
    public static class EnumType {
        /**
         * 枚举类型名称
         */
        private String name;
        /**
         * 枚举类型描述
         */
        private String description;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    /**
     * 枚举
     */
    public static class Enum {
        /**
         * 枚举代码
         */
        private Integer code;
        /**
         * 枚举名
         */
        private String name;
        /**
         * 枚举值
         */
        private String value;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
