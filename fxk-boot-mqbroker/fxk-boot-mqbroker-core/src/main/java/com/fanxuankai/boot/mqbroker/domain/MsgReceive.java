package com.fanxuankai.boot.mqbroker.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;

/**
 * @author fanxuankai
 */
@EqualsAndHashCode(callSuper = true)
@TableName("mq_broker_msg_receive")
public class MsgReceive extends Msg {
}
