package com.fanxuankai.boot.log;

import com.fanxuankai.boot.log.domain.Log;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author fanxuankai
 */
public class JdbcLogStore implements LogStore {
    private final JdbcTemplate jdbcTemplate;
    private static final String DEFAULT_INSERT_STATEMENT = "INSERT INTO sys_log(`description`, " +
            "`log_type`, `class_name`, `method_name`, `params`, `request_ip`, `time`, `username`, `address`, " +
            "`browser`, `exception_detail`, `create_time`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private String insertSql = DEFAULT_INSERT_STATEMENT;

    public JdbcLogStore(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void store(Log log) {
        jdbcTemplate.update(insertSql, log.getDescription(), log.getLogType(), log.getClassName(),
                log.getMethodName(), log.getParams(), log.getRequestIp(), log.getTime(), log.getUsername(),
                log.getAddress(), log.getBrowser(), log.getExceptionDetail(), log.getCreateTime());
    }

    public void setInsertSql(String insertSql) {
        this.insertSql = insertSql;
    }
}