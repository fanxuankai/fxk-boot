package com.fanxuankai.boot.log;

import com.fanxuankai.boot.log.domain.Log;

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
     * @param log 日志信息
     */
    void store(Log log);
}