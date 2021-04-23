package com.fanxuankai.boot.canal.mqbroker.consumer;

import com.fanxuankai.boot.mqbroker.model.Event;
import com.fanxuankai.boot.mqbroker.produce.EventPublisher;
import com.fanxuankai.canal.core.config.ConsumerConfig;
import com.fanxuankai.canal.core.model.EntryWrapper;
import com.fanxuankai.canal.core.util.CommonUtils;
import com.fanxuankai.canal.mq.core.config.CanalMqConfiguration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 更新事件消费者
 *
 * @author fanxuankai
 */
public class UpdateConsumer extends AbstractConsumer {

    public UpdateConsumer(CanalMqConfiguration canalMqConfiguration, EventPublisher<String> eventPublisher) {
        super(canalMqConfiguration, eventPublisher);
    }

    @Override
    public List<Event<String>> apply(EntryWrapper entryWrapper) {
        String group = getGroup(entryWrapper);
        String topic = getTopic(entryWrapper);
        String schemaName = entryWrapper.getSchemaName();
        String tableName = entryWrapper.getTableName();
        ConsumerConfig consumerConfig = getConsumerConfig(entryWrapper);
        return entryWrapper.getAllRowDataList()
                .stream()
                .map(rowData -> {
                    Event<String> event = new Event<>();
                    event.setGroup(group);
                    event.setName(topic);
                    event.setKey(CommonUtils.md5(rowData.getAfterColumnsList()));
                    event.setData(CommonUtils.jsonWithActualType(consumerConfig,
                            rowData.getBeforeColumnsList(),
                            rowData.getAfterColumnsList(), schemaName, tableName, false));
                    return event;
                })
                .collect(Collectors.toList());
    }
}
