package com.fanxuankai.boot.mybatis.plus.autoconfigure;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

/**
 * @author fanxuankai
 */
@EnableConfigurationProperties(MybatisPlusProperties.class)
public class MybatisPlusAutoconfigure {

    /**
     * 分页插件配置
     *
     * @param mybatisPlusProperties 配置
     * @return PaginationInterceptor
     */
    @Bean
    @ConditionalOnMissingBean
    public PaginationInterceptor paginationInterceptor(MybatisPlusProperties mybatisPlusProperties) {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        Optional.ofNullable(mybatisPlusProperties.getPaginationInterceptor().getLimit())
                .ifPresent(paginationInterceptor::setLimit);
        return paginationInterceptor;
    }

    /**
     * 公共字段自动写入配置
     *
     * @return MetaObjectHandler
     */
    @Bean
    @ConditionalOnMissingBean
    public MetaObjectHandler metaObjectHandler() {
        return new DefaultMetaObjectHandler();
    }

    /**
     * 批量插入插件配置
     *
     * @return ISqlInjector
     */
    @Bean
    @ConditionalOnMissingBean
    public ISqlInjector defaultSqlInjector() {
        return new InsertBatchInjector();
    }

}
