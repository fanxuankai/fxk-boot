package com.fanxuankai.boot.mqbroker.produce;

import com.fanxuankai.boot.mqbroker.domain.MsgSend;
import com.fanxuankai.boot.mqbroker.model.Event;
import com.fanxuankai.boot.mqbroker.service.MsgSendService;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author fanxuankai
 */
public abstract class AbstractMqProducer implements MqProducer<MsgSend>, Consumer<Event<String>> {

    @Resource
    private MsgSendService msgSendService;

    @Override
    public void produce(MsgSend msg) {
        Event<String> event = new Event<String>()
                .setGroup(msg.getMsgGroup())
                .setName(msg.getTopic())
                .setKey(msg.getCode())
                .setData(msg.getData());
        event.setEffectTime(msg.getEffectTime());
        Optional.ofNullable(msg.getRetryCount())
                .ifPresent(event::setRetryCount);
        accept(event);
        if (!isPublisherCallback()) {
            msgSendService.success(msg);
        }
    }

}
