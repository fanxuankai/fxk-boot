package com.fanxuankai.boot.canal.mqbroker;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.fanxuankai.boot.canal.mqbroker.consumer.DeleteConsumer;
import com.fanxuankai.boot.canal.mqbroker.consumer.InsertConsumer;
import com.fanxuankai.boot.canal.mqbroker.consumer.UpdateConsumer;
import com.fanxuankai.boot.mqbroker.produce.EventPublisher;
import com.fanxuankai.canal.core.CanalWorker;
import com.fanxuankai.canal.core.ConsumerConfigFactory;
import com.fanxuankai.canal.core.EntryConsumerFactory;
import com.fanxuankai.canal.core.config.CanalConfiguration;
import com.fanxuankai.canal.core.config.CanalWorkConfiguration;
import com.fanxuankai.canal.mq.core.config.CanalMqConfiguration;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * @author fanxuankai
 */
public class CanalMqBrokerWorker extends CanalWorker {

    public CanalMqBrokerWorker(CanalWorkConfiguration canalWorkConfiguration) {
        super(canalWorkConfiguration);
    }

    public static CanalMqBrokerWorker newCanalWorker(CanalConfiguration canalConfiguration,
                                                     @Nullable CanalMqConfiguration canalMqConfiguration,
                                                     EventPublisher<String> eventPublisher) {
        ConsumerConfigFactory consumerConfigFactory = new ConsumerConfigFactory();
        canalMqConfiguration = Optional.ofNullable(canalMqConfiguration)
                .orElse(new CanalMqConfiguration());
        canalMqConfiguration.getConsumerConfigMap().forEach((schema, consumerConfigMap) ->
                consumerConfigMap.forEach((table, consumerConfig) ->
                        consumerConfigFactory.put(schema, table, consumerConfig)));
        EntryConsumerFactory entryConsumerFactory = new EntryConsumerFactory();
        entryConsumerFactory.put(CanalEntry.EventType.INSERT, new InsertConsumer(canalMqConfiguration, eventPublisher));
        entryConsumerFactory.put(CanalEntry.EventType.UPDATE, new UpdateConsumer(canalMqConfiguration, eventPublisher));
        entryConsumerFactory.put(CanalEntry.EventType.DELETE, new DeleteConsumer(canalMqConfiguration, eventPublisher));
        CanalWorkConfiguration canalWorkConfiguration = new CanalWorkConfiguration();
        canalWorkConfiguration.setCanalConfiguration(canalConfiguration);
        canalWorkConfiguration.setConsumerConfigFactory(consumerConfigFactory);
        canalWorkConfiguration.setEntryConsumerFactory(entryConsumerFactory);
        return new CanalMqBrokerWorker(canalWorkConfiguration);
    }

}
