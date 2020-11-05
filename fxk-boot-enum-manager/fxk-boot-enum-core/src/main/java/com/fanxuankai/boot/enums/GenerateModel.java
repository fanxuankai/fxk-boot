package com.fanxuankai.boot.enums;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 代码生成 model
 *
 * @author fanxuankai
 */
@Data
@Accessors(chain = true)
public class GenerateModel {
    /**
     * 作者
     */
    private String auth;
    /**
     * 枚举所在包
     */
    private String packageName;
    /**
     * 文件路径
     */
    private String path;
}
