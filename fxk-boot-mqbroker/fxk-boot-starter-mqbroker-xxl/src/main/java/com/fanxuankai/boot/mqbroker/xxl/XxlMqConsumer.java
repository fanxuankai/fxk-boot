package com.fanxuankai.boot.mqbroker.xxl;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
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
        return JSONUtil.toBean(s, new TypeReference<Event<String>>() {
        }, true);
    }

    @Override
    public MqResult consume(String s) {
        accept(s);
        return MqResult.SUCCESS;
    }
}
