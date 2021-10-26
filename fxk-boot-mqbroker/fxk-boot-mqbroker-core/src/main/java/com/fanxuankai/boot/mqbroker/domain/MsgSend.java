package com.fanxuankai.boot.mqbroker.domain;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @author fanxuankai
 */
@TableName("mq_broker_msg_send")
public class MsgSend extends Msg {
    /**
     * 重试次数
     */
    private Integer retryCount;
    /**
     * 生效时间
     */
    private Date effectTime;

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public Date getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(Date effectTime) {
        this.effectTime = effectTime;
    }
}
