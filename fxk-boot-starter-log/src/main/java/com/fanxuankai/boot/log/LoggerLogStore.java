package com.fanxuankai.boot.log;

import com.fanxuankai.boot.log.domain.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * @author fanxuankai
 */
public class LoggerLogStore implements LogStore {
    private static final String ERROR = "ERROR";

    @Override
    public void store(Log log) {
        Logger logger = LoggerFactory.getLogger(log.getClassName());
        if (Objects.equals(log.getLogType(), ERROR)) {
            logger.error(log.toString());
        } else {
            logger.info(log.toString());
        }
    }
}