package com.fanxuankai.boot.log;

import com.fanxuankai.boot.log.autoconfigure.LogProperties;
import com.fanxuankai.boot.log.domain.Log;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author fanxuankai
 */
public class JdbcLogStore implements LogStore {
    private static final String DEFAULT_TABLE_NAME = "sys_log";
    private static final String INSERT_STATEMENT = "INSERT INTO %s(`description`, " +
            "`log_level`, `class_name`, `method_name`, `params`, `client_ip`, `client_address`, `time`, `username`, " +
            "`browser`, `exception_detail`, `create_time`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private final LogProperties logProperties;
    private final JdbcTemplate jdbcTemplate;

    public JdbcLogStore(LogProperties logProperties, DataSource dataSource) {
        this.logProperties = logProperties;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void store(Log log) {
        jdbcTemplate.update(String.format(INSERT_STATEMENT, getTableName()), log.getDescription(), log.getLogType(),
                log.getClassName(), log.getMethodName(), log.getParams(), log.getClientIp(), log.getClientAddress(),
                log.getTime(), log.getUsername(), log.getBrowser(), log.getExceptionDetail(), log.getCreateTime());
    }

    private String getTableName() {
        if (logProperties == null || logProperties.getJdbcStore() == null) {
            return DEFAULT_TABLE_NAME;
        }
        return logProperties.getJdbcStore().getTableName();
    }
}