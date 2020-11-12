package com.fanxuankai.boot.mqbroker.produce;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fanxuankai.boot.mqbroker.domain.Msg;
import com.fanxuankai.boot.mqbroker.domain.MsgSend;
import com.fanxuankai.boot.mqbroker.enums.Status;
import com.fanxuankai.boot.mqbroker.model.Event;
import com.fanxuankai.boot.mqbroker.service.MqBrokerDingTalkClientHelper;
import com.fanxuankai.boot.mqbroker.service.MsgSendService;
import com.fanxuankai.commons.util.ThrowableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
public abstract class AbstractEventPublisher<T> implements EventPublisher<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEventPublisher.class);

    @Resource
    protected MsgSendService msgSendService;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;
    @Resource
    private MqBrokerDingTalkClientHelper mqBrokerDingTalkClientHelper;

    private boolean exists(Event<T> event) {
        return msgSendService.count(Wrappers.lambdaQuery(MsgSend.class)
                .eq(StringUtils.hasText(event.getGroup()), Msg::getMsgGroup, event.getGroup())
                .isNull(!StringUtils.hasText(event.getGroup()), Msg::getMsgGroup)
                .eq(Msg::getTopic, event.getName())
                .eq(Msg::getCode, event.getKey())) > 0;
    }

    protected void persistence(Event<T> event, boolean async) {
        if (exists(event)) {
            LOGGER.info(String.format("防重生产, group: %s topic: %s code: %s", event.getGroup(), event.getName(),
                    event.getKey()));
            mqBrokerDingTalkClientHelper.push("防重生产", event.getGroup(), event.getName(), event.getKey());
            return;
        }
        MsgSend msgSend = createMessageSend(event);
        if (async) {
            threadPoolExecutor.execute(() -> save(msgSend));
        } else {
            save(msgSend);
        }
    }

    protected void persistence(List<Event<T>> events, boolean async) {
        if (events.size() == 1) {
            persistence(events.get(0), async);
            return;
        }
        events = events.stream()
                .filter(event -> {
                    if (exists(event)) {
                        LOGGER.info(String.format("防重生产, group: %s topic: %s code: %s", event.getGroup(),
                                event.getName(),
                                event.getKey()));
                        mqBrokerDingTalkClientHelper.push("防重生产", event.getGroup(), event.getName(), event.getKey());
                        return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(events)) {
            return;
        }
        List<MsgSend> msgSends = events.stream()
                .map(this::createMessageSend)
                .collect(Collectors.toList());
        if (async) {
            threadPoolExecutor.execute(() -> save(msgSends));
        } else {
            save(msgSends);
        }
    }

    private MsgSend createMessageSend(Event<?> event) {
        MsgSend msg = new MsgSend();
        msg.setMsgGroup(event.getGroup());
        msg.setTopic(event.getName());
        msg.setCode(event.getKey());
        Object data = event.getData();
        if (data instanceof CharSequence) {
            msg.setData(data.toString());
        } else {
            msg.setData(JSON.toJSONString(data));
        }
        msg.setRetry(0);
        msg.setStatus(Status.RUNNING.getCode());
        Optional.ofNullable(event.getRetryCount())
                .ifPresent(msg::setRetryCount);
        Date now = new Date();
        msg.setEffectTime(Optional.ofNullable(event.getEffectTime())
                .orElse(now));
        msg.setCreateDate(now);
        msg.setLastModifiedDate(now);
        return msg;
    }

    private void save(MsgSend msgSend) {
        try {
            if (msgSendService.save(msgSend)) {
                produce(msgSend);
            }
        } catch (Throwable throwable) {
            ThrowableUtils.checkException(throwable, DuplicateKeyException.class,
                    SQLIntegrityConstraintViolationException.class);
        }
    }

    private void save(List<MsgSend> msgSends) {
        try {
            if (msgSendService.saveBatch(msgSends)) {
                msgSends.forEach(this::produce);
            }
        } catch (Throwable throwable) {
            ThrowableUtils.checkException(throwable, DuplicateKeyException.class,
                    SQLIntegrityConstraintViolationException.class);
        }
    }

    private void produce(MsgSend msg) {
        msgSendService.produce(msg, true);
    }
}
