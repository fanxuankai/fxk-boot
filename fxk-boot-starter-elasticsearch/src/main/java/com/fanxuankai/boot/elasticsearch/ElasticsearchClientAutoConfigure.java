package com.fanxuankai.boot.elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @author fanxuankai
 */
@EnableConfigurationProperties(ElasticsearchClientProperties.class)
public class ElasticsearchClientAutoConfigure {
    @Bean
    public RestHighLevelClient restHighLevelClient(ElasticsearchClientProperties properties) {
        ClientConfiguration.MaybeSecureClientConfigurationBuilder builder =
                ClientConfiguration.builder()
                        .connectedTo(Optional.ofNullable(properties.getHostAndPorts())
                                .filter(o -> !CollectionUtils.isEmpty(o))
                                .map(o -> o.toArray(new String[0]))
                                .orElse(new String[]{"localhost:9200"}));
        if (StringUtils.hasText(properties.getUser()) && StringUtils.hasText(properties.getPassword())) {
            builder.withBasicAuth(properties.getUser(), properties.getPassword());
        }
        return RestClients.create(builder
                .withConnectTimeout(Optional.ofNullable(properties.getConnectTimeout()).orElse(10000L))
                .withSocketTimeout(Optional.ofNullable(properties.getSocketTimeout()).orElse(5000L))
                .build())
                .rest();
    }

}
