package com.fanxuankai.boot.log;

import com.fanxuankai.boot.log.domain.Log;

/**
 * @author fanxuankai
 */
public interface LogStore {
    /**
     * 存储日志
     *
     * @param log 日志信息
     */
    void store(Log log);
}