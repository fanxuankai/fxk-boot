package com.fanxuankai.boot.log;

/**
 * 日志存储接口
 *
 * @author fanxuankai
 * @see LoggerLogStore
 * @see JdbcLogStore
 */
public interface LogStore {
    /**
     * 存储日志
     *
     * @param logInfo 日志信息
     */
    void store(LogInfo logInfo);
}