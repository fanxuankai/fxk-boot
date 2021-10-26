package com.fanxuankai.boot.mqbroker.produce;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fanxuankai.boot.mqbroker.autoconfigure.MqBrokerProperties;
import com.fanxuankai.boot.mqbroker.domain.Msg;
import com.fanxuankai.boot.mqbroker.domain.MsgSend;
import com.fanxuankai.boot.mqbroker.enums.SendStatus;
import com.fanxuankai.boot.mqbroker.model.Event;
import com.fanxuankai.boot.mqbroker.service.MqBrokerDingTalkClientHelper;
import com.fanxuankai.boot.mqbroker.service.MsgSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
    @Resource
    private MqBrokerProperties mqBrokerProperties;

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
        Date now = new Date();
        msg.setEffectTime(Optional.ofNullable(event.getEffectTime())
                .orElse(now));
        if (mqBrokerProperties.getDelayedSend().isEnabled()
                && !msg.getEffectTime().before(now)) {
            msg.setStatus(SendStatus.DELAYED.getCode());
        } else {
            msg.setStatus(SendStatus.WAIT.getCode());
        }
        Optional.ofNullable(event.getRetryCount())
                .ifPresent(msg::setRetryCount);
        msg.setCreateDate(now);
        msg.setLastModifiedDate(now);
        return msg;
    }

    @SuppressWarnings("unchecked")
    private void save(MsgSend msgSend) {
        try {
            if (msgSendService.save(msgSend)
                    && Objects.equals(msgSend.getStatus(), SendStatus.WAIT.getCode())) {
                produce(msgSend);
            }
        } catch (Throwable throwable) {
            if (!ExceptionUtil.isCausedBy(throwable, DuplicateKeyException.class,
                    SQLIntegrityConstraintViolationException.class)) {
                throw new RuntimeException(throwable);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void save(List<MsgSend> msgSends) {
        try {
            if (msgSendService.saveBatch(msgSends)) {
                msgSends.stream()
                        .filter(msgSend -> Objects.equals(msgSend.getStatus(), SendStatus.WAIT.getCode()))
                        .forEach(this::produce);
            }
        } catch (Throwable throwable) {
            if (!ExceptionUtil.isCausedBy(throwable, DuplicateKeyException.class,
                    SQLIntegrityConstraintViolationException.class)) {
                throw new RuntimeException(throwable);
            }
        }
    }

    private void produce(MsgSend msg) {
        msgSendService.produce(msg, true);
    }
}
