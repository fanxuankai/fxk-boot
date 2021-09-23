package com.fanxuankai.boot.canal.elasticsearch.autoconfigure;

import com.fanxuankai.canal.elasticsearch.CanalElasticsearchWorker;
import com.fanxuankai.canal.elasticsearch.IndexDefinitionManager;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

/**
 * @author fanxuankai
 */
@EnableConfigurationProperties(CanalEsProperties.class)
public class CanalEsAutoConfiguration {

    private final IndexDefinitionManager indexDefinitionManager;

    public CanalEsAutoConfiguration(IndexDefinitionManager indexDefinitionManager) {
        this.indexDefinitionManager = indexDefinitionManager;
    }

    @Bean
    @ConditionalOnProperty(prefix = CanalEsProperties.PREFIX, name = "enabled", havingValue = "true")
    @ConditionalOnMissingBean
    public CanalElasticsearchWorker canalElasticsearchWorker(CanalEsProperties canalEsProperties,
                                                             ElasticsearchRestTemplate elasticsearchRestTemplate,
                                                             RestHighLevelClient restHighLevelClient) {
        return CanalElasticsearchWorker.newCanalWorker(canalEsProperties.getConfiguration(), canalEsProperties,
                indexDefinitionManager, elasticsearchRestTemplate, restHighLevelClient);
    }
}
