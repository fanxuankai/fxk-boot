package com.fanxuankai.boot.canal.clickhouse.config;

import com.fanxuankai.canal.clickhouse.CanalClickhouseWorker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author fanxuankai
 */
@EnableConfigurationProperties(CanalClickhouseProperties.class)
public class CanalClickhouseAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = CanalClickhouseProperties.PREFIX, name = "enabled", havingValue = "true")
    @ConditionalOnMissingBean
    public CanalClickhouseWorker canalClickhouseWorker(CanalClickhouseProperties canalClickhouseProperties,
                                                       JdbcTemplate jdbcTemplate) {
        return CanalClickhouseWorker.newCanalWorker(canalClickhouseProperties.getConfiguration(),
                canalClickhouseProperties, jdbcTemplate);
    }

}
