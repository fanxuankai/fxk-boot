package com.fanxuankai.boot.generator.service;

/**
 * 列的数据信息
 *
 * @author fanxuankai
 */
public interface GeneratorService {
    /**
     * 初始化表配置
     *
     * @param tableName 表
     */
    void initConfig(String tableName);

    /**
     * 生成代码
     *
     * @param tableName 表名
     */
    void generateCode(String tableName);
}
