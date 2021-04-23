package com.fanxuankai.boot.canal.mysql;

import com.fanxuankai.canal.mysql.CanalMySqlWorker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author fanxuankai
 */
@EnableConfigurationProperties(CanalMySqlProperties.class)
public class CanalMySqlAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = CanalMySqlProperties.PREFIX, name = "enabled", havingValue = "true")
    @ConditionalOnMissingBean
    public CanalMySqlWorker canalMySqlWorker(CanalMySqlProperties canalMySqlProperties, JdbcTemplate jdbcTemplate) {
        return CanalMySqlWorker.newCanalWorker(canalMySqlProperties.getConfiguration(), canalMySqlProperties,
                jdbcTemplate);
    }

}
