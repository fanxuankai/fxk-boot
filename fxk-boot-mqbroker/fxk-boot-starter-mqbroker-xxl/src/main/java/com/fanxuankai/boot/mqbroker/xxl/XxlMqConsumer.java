package com.fanxuankai.boot.mqbroker.xxl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fanxuankai.boot.mqbroker.consume.AbstractMqConsumer;
import com.fanxuankai.boot.mqbroker.model.Event;
import com.xxl.mq.client.consumer.IMqConsumer;
import com.xxl.mq.client.consumer.MqResult;

/**
 * @author fanxuankai
 */
public class XxlMqConsumer extends AbstractMqConsumer<String> implements IMqConsumer {
    @Override
    public Event<String> apply(String s) {
        return JSON.parseObject(s, new TypeReference<Event<String>>() {
        });
    }

    @Override
    public MqResult consume(String s) {
        accept(s);
        return MqResult.SUCCESS;
    }
}
