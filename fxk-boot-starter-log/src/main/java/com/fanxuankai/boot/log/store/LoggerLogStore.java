package com.fanxuankai.boot.log.store;

import cn.hutool.json.JSONUtil;
import com.fanxuankai.boot.log.LogInfo;
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