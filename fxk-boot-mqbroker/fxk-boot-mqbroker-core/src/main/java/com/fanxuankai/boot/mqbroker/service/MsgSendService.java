package com.fanxuankai.boot.mqbroker.service;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fanxuankai.boot.mqbroker.domain.Msg;
import com.fanxuankai.boot.mqbroker.domain.MsgSend;
import com.fanxuankai.boot.mqbroker.enums.Status;

import java.util.List;

/**
 * @author fanxuankai
 */
public interface MsgSendService extends IService<MsgSend> {
    /**
     * 拉取消息
     *
     * @return list
     */
    List<MsgSend> pullData();

    /**
     * 锁定消息
     *
     * @param id 消息id
     * @return 是否成功
     */
    boolean lock(Long id);

    /**
     * 回调超时
     */
    void publisherCallbackTimeout();

    /**
     * success
     *
     * @param msg 消息
     */
    void success(MsgSend msg);

    /**
     * success
     *
     * @param topic 主题
     * @param code  code
     */
    void success(String topic, String code);

    /**
     * failure
     *
     * @param topic 主题
     * @param code  code
     * @param cause 原因
     */
    void failure(String topic, String code, String cause);

    /**
     * failure
     *
     * @param msg 消息
     */
    void failure(MsgSend msg);

    /**
     * 更新重试次数
     *
     * @param msg 消息
     */
    void updateRetry(MsgSend msg);

    /**
     * 发送消息
     *
     * @param msg   消息
     * @param retry 是否重试
     */
    void produce(MsgSend msg, boolean retry);

    /**
     * 补偿
     *
     * @param ids ids
     */
    default void compensate(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        MsgSend entity = new MsgSend();
        entity.setRetry(0);
        entity.setStatus(Status.CREATED.getCode());
        update(entity, Wrappers.lambdaUpdate(MsgSend.class)
                .in(Msg::getId, ids)
        );
        for (MsgSend msgSend : listByIds(ids)) {
            if (lock(msgSend.getId())) {
                produce(msgSend, true);
            }
        }
    }
}
