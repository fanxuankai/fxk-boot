package com.fanxuankai.boot.mqbroker.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fanxuankai
 */
@EqualsAndHashCode(callSuper = true)
@TableName("mq_broker_msg_send")
@Data
public class MsgSend extends Msg {

}
