package com.fanxuankai.boot.mqbroker.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.json.JSONUtil;
import com.fanxuankai.boot.mqbroker.autoconfigure.MqBrokerProperties;
import com.fanxuankai.boot.mqbroker.consume.AbstractEventDistributor;
import com.fanxuankai.boot.mqbroker.consume.EventDistributorFactory;
import com.fanxuankai.boot.mqbroker.consume.EventListenerRegistry;
import com.fanxuankai.boot.mqbroker.domain.MsgReceive;
import com.fanxuankai.boot.mqbroker.model.Event;
import com.fanxuankai.boot.mqbroker.model.ListenerMetadata;
import com.fanxuankai.boot.mqbroker.service.MsgReceiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author fanxuankai
 */
@Service
public class MsgReceiveConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgReceiveConsumer.class);
    @Resource
    private MsgReceiveService msgReceiveService;
    @Resource
    private EventDistributorFactory eventDistributorFactory;
    @Resource
    private MqBrokerProperties mqBrokerProperties;
    @Resource
    private DataSourceTransactionManager dataSourceTransactionManager;
    @Resource
    private TransactionDefinition transactionDefinition;
    @Resource
    private EventListenerRegistry eventListenerRegistry;

    /**
     * 消费消息
     *
     * @param msg   消息
     * @param retry 是否重试
     */
    public void consume(MsgReceive msg, boolean retry) {
        int i = msg.getRetry();
        msg = BeanUtil.copyProperties(msg, MsgReceive.class);
        ListenerMetadata listenerMetadata = new ListenerMetadata();
        listenerMetadata.setGroup(msg.getMsgGroup());
        listenerMetadata.setTopic(msg.getTopic());
        Event<Object> event = new Event<>();
        event.setGroup(msg.getMsgGroup());
        event.setName(msg.getTopic());
        event.setKey(msg.getCode());
        event.setData(JSONUtil.toBean(msg.getData(), eventListenerRegistry.getDataType(listenerMetadata),
                true));
        event.setRetryCount(msg.getRetryCount());
        AbstractEventDistributor abstractEventDistributor = eventDistributorFactory.get(msg);
        while (true) {
            TransactionStatus transactionStatus = null;
            try {
                // 手动开启事务
                transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
                abstractEventDistributor.distribute(event);
                msgReceiveService.success(msg);
                // 手动提交事务
                dataSourceTransactionManager.commit(transactionStatus);
                break;
            } catch (Throwable throwable) {
                // 手动回滚事务
                if (transactionStatus != null) {
                    dataSourceTransactionManager.rollback(transactionStatus);
                }
                LOGGER.error("消息消费失败, code: " + msg.getCode(), throwable);
                msg.setCause(ExceptionUtil.stacktraceToString(throwable));
                msg.setRetry(++i);
                if (retry && i < mqBrokerProperties.getMaxRetry()) {
                    ThreadUtil.sleep(1, TimeUnit.SECONDS);
                } else {
                    msgReceiveService.failure(msg);
                    break;
                }
            }
        }
    }
}
