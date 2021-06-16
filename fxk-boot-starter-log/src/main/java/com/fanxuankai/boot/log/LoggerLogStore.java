package com.fanxuankai.boot.log;

import com.fanxuankai.boot.log.domain.Log;
import org.slf4j.LoggerFactory;

/**
 * @author fanxuankai
 */
public class LoggerLogStore implements LogStore {
    @Override
    public void store(Log log) {
        LoggerFactory.getLogger(log.getClassName()).info(log.toString());
    }
}