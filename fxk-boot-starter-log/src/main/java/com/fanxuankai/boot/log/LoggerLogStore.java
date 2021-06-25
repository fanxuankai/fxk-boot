package com.fanxuankai.boot.log;

import cn.hutool.json.JSONUtil;
import org.slf4j.LoggerFactory;

/**
 * Logger 记录日志
 *
 * @author fanxuankai
 */
public class LoggerLogStore implements LogStore {
    @Override
    public void store(LogInfo logInfo) {
        LoggerFactory.getLogger(logInfo.getClassName()).info(JSONUtil.toJsonStr(logInfo));
    }
}