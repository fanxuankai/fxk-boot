package com.fanxuankai.boot.log.store;

import com.fanxuankai.boot.log.LogInfo;
import com.fanxuankai.boot.log.autoconfigure.LogProperties;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * JDBC 存储日志
 *
 * @author fanxuankai
 */
public class JdbcLogStore implements LogStore {
    private static final String DEFAULT_TABLE_NAME = "sys_log";
    private static final String DEFAULT_INSERT_STATEMENT = "INSERT INTO %s(`resource`, `url`, `safety_level`, " +
            "`class_name`, `method_name`, `params`, `return_value`, `server_ip`, `client_ip`, `client_address`, " +
            "`browser`, `total_time_millis`, `username`, `operation_exception`, `exception_detail`, `create_time`) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String insertStatement;
    private final LogProperties logProperties;
    private final JdbcTemplate jdbcTemplate;

    public JdbcLogStore(LogProperties logProperties, DataSource dataSource) {
        this.logProperties = logProperties;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.insertStatement = String.format(DEFAULT_INSERT_STATEMENT, getTableName());
    }

    @Override
    public void store(LogInfo logInfo) {
        jdbcTemplate.update(insertStatement, logInfo.getResource(), logInfo.getUrl(),
                logInfo.getSafetyLevel(), logInfo.getClassName(), logInfo.getMethodName(), logInfo.getParams(),
                logInfo.getReturnValue(), logInfo.getServerIp(), logInfo.getClientIp(), logInfo.getClientAddress(),
                logInfo.getBrowser(), logInfo.getTotalTimeMillis(), logInfo.getUsername(),
                logInfo.getOperationException(), logInfo.getExceptionDetail(), logInfo.getCreateTime());
    }

    private String getTableName() {
        if (logProperties == null || logProperties.getJdbcStore() == null) {
            return DEFAULT_TABLE_NAME;
        }
        return logProperties.getJdbcStore().getTableName();
    }
}