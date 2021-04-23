package com.fanxuankai.boot.mqbroker.service.impl;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.mqbroker.config.MqBrokerProperties;
import com.fanxuankai.boot.mqbroker.domain.Msg;
import com.fanxuankai.boot.mqbroker.domain.MsgSend;
import com.fanxuankai.boot.mqbroker.enums.Status;
import com.fanxuankai.boot.mqbroker.mapper.MsgSendMapper;
import com.fanxuankai.boot.mqbroker.produce.MqProducer;
import com.fanxuankai.boot.mqbroker.service.MqBrokerDingTalkClientHelper;
import com.fanxuankai.boot.mqbroker.service.MsgSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author fanxuankai
 */
@Component
public class MsgSendServiceImpl extends ServiceImpl<MsgSendMapper, MsgSend>
        implements MsgSendService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgSendServiceImpl.class);
    @Resource
    private MqBrokerProperties mqBrokerProperties;
    @Resource
    private MqProducer<MsgSend> mqProducer;
    @Resource
    private MqBrokerDingTalkClientHelper mqBrokerDingTalkClientHelper;

    @Override
    public List<MsgSend> pullData() {
        return page(new Page<>(1, mqBrokerProperties.getMsgSize()),
                new QueryWrapper<MsgSend>().lambda()
                        .eq(Msg::getStatus, Status.CREATED.getCode())
                        .orderByAsc(Msg::getId)
                        .lt(Msg::getRetry, mqBrokerProperties.getMaxRetry()))
                .getRecords();
    }

    @Override
    public boolean lock(Long id) {
        MsgSend entity = new MsgSend();
        entity.setStatus(Status.RUNNING.getCode());
        entity.setLastModifiedDate(new Date());
        entity.setHostAddress(NetUtil.getLocalhostStr());
        return update(entity, new UpdateWrapper<MsgSend>()
                .lambda()
                .eq(Msg::getStatus, Status.CREATED.getCode())
                .eq(Msg::getId, id));
    }

    @Override
    public void publisherCallbackTimeout() {
        Date timeout = Date.from(LocalDateTime.now().plus(-mqBrokerProperties.getPublisherCallbackTimeout(),
                ChronoUnit.MILLIS).atZone(ZoneId.systemDefault()).toInstant());
        MsgSend entity = new MsgSend();
        entity.setCause("回调超时");
        entity.setLastModifiedDate(new Date());
        Supplier<LambdaUpdateWrapper<MsgSend>> lambdaSupplier = () -> new UpdateWrapper<MsgSend>()
                .lambda()
                .eq(Msg::getStatus, Status.RUNNING.getCode())
                .lt(Msg::getLastModifiedDate, timeout);
        int lastChance = mqBrokerProperties.getMaxRetry();
        entity.setStatus(Status.CREATED.getCode());
        update(entity, lambdaSupplier.get().lt(Msg::getRetry, lastChance));
        entity.setStatus(Status.FAILURE.getCode());
        update(entity, lambdaSupplier.get().ge(Msg::getRetry, lastChance));
    }

    @Override
    public void success(MsgSend msg) {
        MsgSend entity = new MsgSend();
        entity.setLastModifiedDate(new Date());
        entity.setHostAddress(NetUtil.getLocalhostStr());
        entity.setStatus(Status.SUCCESS.getCode());
        update(entity, new UpdateWrapper<MsgSend>()
                .lambda()
                .eq(Msg::getId, msg.getId())
                .eq(Msg::getStatus, Status.RUNNING.getCode()));
    }

    @Override
    public void success(String topic, String code) {
        MsgSend entity = new MsgSend();
        entity.setLastModifiedDate(new Date());
        entity.setHostAddress(NetUtil.getLocalhostStr());
        entity.setStatus(Status.SUCCESS.getCode());
        update(entity, new UpdateWrapper<MsgSend>()
                .lambda()
                .eq(Msg::getTopic, topic)
                .eq(Msg::getCode, code)
                .eq(Msg::getStatus, Status.RUNNING.getCode()));
    }

    @Override
    public void failure(String topic, String code, String cause) {
        MsgSend msg = getOne(new QueryWrapper<MsgSend>()
                .lambda()
                .eq(Msg::getTopic, topic)
                .eq(Msg::getCode, code));
        if (msg == null) {
            return;
        }
        msg.setCause(cause);
        failure(msg);
    }

    @Override
    public void failure(MsgSend msg) {
        MsgSend entity = new MsgSend();
        entity.setRetry(msg.getRetry());
        entity.setCause(msg.getCause());
        entity.setLastModifiedDate(new Date());
        String hostAddress = NetUtil.getLocalhostStr();
        entity.setHostAddress(hostAddress);
        LambdaUpdateWrapper<MsgSend> lambda = new UpdateWrapper<MsgSend>().lambda()
                .eq(Msg::getId, msg.getId())
                .eq(Msg::getStatus, Status.RUNNING.getCode());
        int lastChance = mqBrokerProperties.getMaxRetry();
        if (msg.getRetry() < lastChance) {
            entity.setStatus(Status.CREATED.getCode());
        } else {
            entity.setStatus(Status.FAILURE.getCode());
        }
        update(entity, lambda);
        mqBrokerDingTalkClientHelper.push("消息发送失败", msg.getMsgGroup(), msg.getTopic(), msg.getCode(), msg.getRetry(),
                hostAddress);
    }

    @Override
    public void updateRetry(MsgSend msg) {
        MsgSend entity = new MsgSend();
        entity.setCause(msg.getCause());
        entity.setRetry(msg.getRetry());
        entity.setLastModifiedDate(new Date());
        entity.setHostAddress(NetUtil.getLocalhostStr());
        update(entity, Wrappers.lambdaUpdate(MsgSend.class).eq(Msg::getId, msg.getId()));
    }

    @Override
    public void produce(MsgSend msg, boolean retry) {
        int i = msg.getRetry();
        boolean success = false;
        do {
            try {
                mqProducer.produce(msg);
                success = true;
            } catch (Throwable throwable) {
                LOGGER.error("消息发送失败, code: " + msg.getCode(), throwable);
                msg.setCause(throwable.getLocalizedMessage());
                ThreadUtil.sleep(1, TimeUnit.SECONDS);
            }
        } while (!success && retry && ++i < mqBrokerProperties.getMaxRetry());
        msg.setRetry(i);
        if (success) {
            if (i > msg.getRetry()) {
                updateRetry(msg);
            }
        } else {
            failure(msg);
        }
    }
}
