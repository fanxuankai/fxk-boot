package com.fanxuankai.boot.mqbroker.consume;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fanxuankai.boot.mqbroker.domain.Msg;
import com.fanxuankai.boot.mqbroker.domain.MsgReceive;
import com.fanxuankai.boot.mqbroker.enums.ReceiveStatus;
import com.fanxuankai.boot.mqbroker.model.Event;
import com.fanxuankai.boot.mqbroker.service.MqBrokerDingTalkClientHelper;
import com.fanxuankai.boot.mqbroker.service.MsgReceiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.util.StringUtils;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.function.Function;

/**
 * @author fanxuankai
 */
public abstract class AbstractMqConsumer<T> implements MqConsumer<T>, Function<T, Event<String>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMqConsumer.class);
    private MsgReceiveService msgReceiveService;
    private MqBrokerDingTalkClientHelper mqBrokerDingTalkClientHelper;

    private MsgReceiveService getMsgReceiveService() {
        if (msgReceiveService == null) {
            msgReceiveService = SpringUtil.getBean(MsgReceiveService.class);
        }
        return msgReceiveService;
    }

    public MqBrokerDingTalkClientHelper getMqBrokerDingTalkClientHelper() {
        if (mqBrokerDingTalkClientHelper == null) {
            mqBrokerDingTalkClientHelper = SpringUtil.getBean(MqBrokerDingTalkClientHelper.class);
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
    @SuppressWarnings("unchecked")
    public void accept(T t) {
        Event<String> event = apply(t);
        MsgReceiveService msgReceiveService = getMsgReceiveService();
        if (exists(event)) {
            LOGGER.info(String.format("防重消费, group: %s topic: %s code: %s", event.getGroup(), event.getName(),
                    event.getKey()));
            return;
        }
        MsgReceive msg = new MsgReceive();
        msg.setMsgGroup(event.getGroup());
        msg.setTopic(event.getName());
        msg.setCode(event.getKey());
        msg.setData(event.getData());
        msg.setStatus(ReceiveStatus.CONSUMING.getCode());
        msg.setRetry(0);
        Date now = new Date();
        msg.setCreateDate(now);
        msg.setLastModifiedDate(now);
        try {
            if (msgReceiveService.save(msg)) {
                msgReceiveService.consume(msg, true, true);
            }
        } catch (Throwable throwable) {
            if (!ExceptionUtil.isCausedBy(throwable, DuplicateKeyException.class,
                    SQLIntegrityConstraintViolationException.class)) {
                throw new RuntimeException(throwable);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Event<String> apply(T t) {
        return (Event<String>) t;
    }
}
