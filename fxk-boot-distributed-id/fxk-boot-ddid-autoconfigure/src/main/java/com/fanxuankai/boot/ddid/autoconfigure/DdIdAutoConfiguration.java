package com.fanxuankai.boot.ddid.autoconfigure;

import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import com.fanxuankai.commons.util.DangDangIdGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author fanxuankai
 */
public class DdIdAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public IdGenerator idGenerator() {
        return DangDangIdGenerator.getInstance();
    }
}
