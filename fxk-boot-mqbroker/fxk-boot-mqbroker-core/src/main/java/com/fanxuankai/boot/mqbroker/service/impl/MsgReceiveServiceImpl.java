package com.fanxuankai.boot.mqbroker.service.impl;

import cn.hutool.core.net.NetUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.mqbroker.autoconfigure.MqBrokerProperties;
import com.fanxuankai.boot.mqbroker.consume.EventListenerRegistry;
import com.fanxuankai.boot.mqbroker.domain.Msg;
import com.fanxuankai.boot.mqbroker.domain.MsgReceive;
import com.fanxuankai.boot.mqbroker.enums.ReceiveStatus;
import com.fanxuankai.boot.mqbroker.mapper.MsgReceiveMapper;
import com.fanxuankai.boot.mqbroker.model.ListenerMetadata;
import com.fanxuankai.boot.mqbroker.service.MqBrokerDingTalkClientHelper;
import com.fanxuankai.boot.mqbroker.service.MsgReceiveService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
@Component
public class MsgReceiveServiceImpl extends ServiceImpl<MsgReceiveMapper, MsgReceive>
        implements MsgReceiveService {
    @Resource
    private MqBrokerProperties mqBrokerProperties;
    @Resource
    private MsgReceiveConsumer msgReceiveConsumer;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;
    @Resource
    private MqBrokerDingTalkClientHelper mqBrokerDingTalkClientHelper;
    @Resource
    private EventListenerRegistry eventListenerRegistry;

    @Override
    public List<MsgReceive> pullData() {
        if (eventListenerRegistry.getAllListenerMetadata().isEmpty()) {
            return Collections.emptyList();
        }
        return page(new Page<>(1, mqBrokerProperties.getMsgSize()),
                Wrappers.lambdaQuery(MsgReceive.class)
                        .eq(Msg::getStatus, ReceiveStatus.WAIT.getCode())
                        .in(Msg::getTopic, eventListenerRegistry.getAllListenerMetadata()
                                .stream()
                                .map(ListenerMetadata::getTopic)
                                .collect(Collectors.toList())
                        )
                        .orderByAsc(MsgReceive::getId)
                        .lt(MsgReceive::getRetry, mqBrokerProperties.getMaxRetry())).getRecords();
    }

    @Override
    public boolean lock(Long id) {
        MsgReceive entity = new MsgReceive();
        entity.setStatus(ReceiveStatus.CONSUMING.getCode());
        entity.setLastModifiedDate(new Date());
        entity.setHostAddress(NetUtil.getLocalhostStr());
        return update(entity, Wrappers.lambdaUpdate(MsgReceive.class)
                .eq(Msg::getStatus, ReceiveStatus.WAIT.getCode())
                .eq(Msg::getId, id));
    }

    @Override
    public void consumeTimeout() {
        Date timeout = Date.from(LocalDateTime.now().plus(-mqBrokerProperties.getConsumeTimeout(),
                ChronoUnit.MILLIS).atZone(ZoneId.systemDefault()).toInstant());
        MsgReceive entity = new MsgReceive();
        entity.setCause("消费超时");
        entity.setLastModifiedDate(new Date());
        Supplier<LambdaUpdateWrapper<MsgReceive>> lambdaSupplier = () ->
                Wrappers.lambdaUpdate(MsgReceive.class)
                        .eq(Msg::getStatus, ReceiveStatus.CONSUMING.getCode())
                        .lt(Msg::getLastModifiedDate, timeout);
        entity.setStatus(ReceiveStatus.WAIT.getCode());
        int lastChance = mqBrokerProperties.getMaxRetry();
        update(entity, lambdaSupplier.get().lt(Msg::getRetry, lastChance));
        entity.setStatus(ReceiveStatus.FAILURE.getCode());
        update(entity, lambdaSupplier.get().ge(Msg::getRetry, lastChance));
    }

    @Override
    public void success(MsgReceive msg) {
        MsgReceive entity = new MsgReceive();
        entity.setLastModifiedDate(new Date());
        entity.setStatus(ReceiveStatus.SUCCESS.getCode());
        entity.setHostAddress(NetUtil.getLocalhostStr());
        update(entity, Wrappers.lambdaUpdate(MsgReceive.class)
                .eq(Msg::getId, msg.getId())
                .eq(Msg::getStatus, ReceiveStatus.CONSUMING.getCode()));
    }

    @Override
    public void failure(MsgReceive msg) {
        MsgReceive entity = new MsgReceive();
        entity.setRetry(msg.getRetry());
        entity.setCause(msg.getCause());
        entity.setLastModifiedDate(new Date());
        String hostAddress = NetUtil.getLocalhostStr();
        entity.setHostAddress(hostAddress);
        if (msg.getRetry() < mqBrokerProperties.getMaxRetry()) {
            entity.setStatus(ReceiveStatus.WAIT.getCode());
        } else {
            entity.setStatus(ReceiveStatus.FAILURE.getCode());
        }
        update(entity, Wrappers.lambdaUpdate(MsgReceive.class)
                .eq(Msg::getId, msg.getId())
                .eq(Msg::getStatus, ReceiveStatus.CONSUMING.getCode()));
        mqBrokerDingTalkClientHelper.push("消息消费失败", msg.getMsgGroup(), msg.getTopic(), msg.getCode(), msg.getRetry(),
                hostAddress);
    }

    @Override
    public void updateRetry(MsgReceive msg) {
        MsgReceive entity = new MsgReceive();
        entity.setCause(msg.getCause());
        entity.setRetry(msg.getRetry());
        entity.setLastModifiedDate(new Date());
        entity.setHostAddress(NetUtil.getLocalhostStr());
        update(entity, Wrappers.lambdaUpdate(MsgReceive.class).eq(Msg::getId, msg.getId()));
    }

    @Override
    public void consume(MsgReceive msg, boolean retry, boolean async) {
        if (async) {
            threadPoolExecutor.execute(() -> msgReceiveConsumer.consume(msg, retry));
        } else {
            msgReceiveConsumer.consume(msg, retry);
        }
    }
}
