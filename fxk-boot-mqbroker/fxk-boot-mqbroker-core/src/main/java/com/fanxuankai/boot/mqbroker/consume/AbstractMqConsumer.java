package com.fanxuankai.boot.mqbroker.consume;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fanxuankai.boot.mqbroker.domain.Msg;
import com.fanxuankai.boot.mqbroker.domain.MsgReceive;
import com.fanxuankai.boot.mqbroker.enums.Status;
import com.fanxuankai.boot.mqbroker.model.Event;
import com.fanxuankai.boot.mqbroker.service.MqBrokerDingTalkClientHelper;
import com.fanxuankai.boot.mqbroker.service.MsgReceiveService;
import com.fanxuankai.commons.util.ThrowableUtils;
import com.fanxuankai.spring.util.ApplicationContexts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.util.StringUtils;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.function.Function;

/**
 * @author fanxuankai
 */
@Slf4j
public abstract class AbstractMqConsumer<T> implements MqConsumer<T>, Function<T, Event<String>> {
    private MsgReceiveService msgReceiveService;
    private MqBrokerDingTalkClientHelper mqBrokerDingTalkClientHelper;

    private MsgReceiveService getMsgReceiveService() {
        if (msgReceiveService == null) {
            msgReceiveService = ApplicationContexts.getBean(MsgReceiveService.class);
        }
        return msgReceiveService;
    }

    public MqBrokerDingTalkClientHelper getMqBrokerDingTalkClientHelper() {
        if (mqBrokerDingTalkClientHelper == null) {
            mqBrokerDingTalkClientHelper = ApplicationContexts.getBean(MqBrokerDingTalkClientHelper.class);
        }
        return mqBrokerDingTalkClientHelper;
    }

    private boolean exists(Event<String> event) {
        return msgReceiveService.count(Wrappers.lambdaQuery(MsgReceive.class)
                .eq(StringUtils.hasText(event.getGroup()), Msg::getMsgGroup, event.getGroup())
                .isNull(!StringUtils.hasText(event.getGroup()), Msg::getMsgGroup)
                .eq(Msg::getTopic, event.getName())
                .eq(Msg::getCode, event.getKey())) > 0;
    }

    @Override
    public void accept(T t) {
        Event<String> event = apply(t);
        MsgReceiveService msgReceiveService = getMsgReceiveService();
        if (exists(event)) {
            log.info(String.format("防重消费, group: %s topic: %s code: %s", event.getGroup(), event.getName(),
                    event.getKey()));
            getMqBrokerDingTalkClientHelper().push("防重消费", event.getGroup(), event.getName(), event.getKey());
            return;
        }
        MsgReceive msg = new MsgReceive();
        msg.setMsgGroup(event.getGroup());
        msg.setTopic(event.getName());
        msg.setCode(event.getKey());
        msg.setData(event.getData());
        msg.setStatus(Status.RUNNING.getCode());
        msg.setRetry(0);
        msg.setRetryCount(event.getRetryCount());
        msg.setEffectTime(event.getEffectTime());
        Date now = new Date();
        msg.setCreateDate(now);
        msg.setLastModifiedDate(now);
        try {
            if (msgReceiveService.save(msg)) {
                msgReceiveService.consume(msg, true, true);
            }
        } catch (Throwable throwable) {
            ThrowableUtils.checkException(throwable, DuplicateKeyException.class,
                    SQLIntegrityConstraintViolationException.class);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Event<String> apply(T t) {
        return (Event<String>) t;
    }
}
