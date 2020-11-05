package com.fanxuankai.boot.enums;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author fanxuankai
 */
@Data
@Accessors(chain = true)
public class EnumModel {
    private String packageName;
    private String auth;
    private EnumVO enumVO;
}
