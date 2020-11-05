package com.fanxuankai.boot.enums;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 枚举数据
 *
 * @author fanxuankai
 */
@Data
@Accessors(chain = true)
public class EnumDTO {

    private EnumType enumType;
    private List<Enum> enumList;

    /**
     * 枚举类型
     */
    @Data
    @Accessors(chain = true)
    public static class EnumType {
        /**
         * 枚举类型名称
         */
        private String name;
        /**
         * 枚举类型描述
         */
        private String description;
    }

    /**
     * 枚举
     */
    @Data
    @Accessors(chain = true)
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
    }
}
