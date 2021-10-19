CREATE TABLE `mq_broker_msg_send`
(
    `id`                 bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `msg_group`          varchar(255)          DEFAULT NULL COMMENT '分组',
    `topic`              varchar(255) NOT NULL COMMENT '队列名',
    `code`               varchar(255) NOT NULL COMMENT '代码',
    `data`               text         NOT NULL COMMENT '内容',
    `status`             int(11)      NOT NULL COMMENT '状态 -1:延迟的 0:正在发送 1:发送中 2:发送成功 3:发送失败',
    `host_address`       varchar(255)          DEFAULT NULL COMMENT '主机地址',
    `retry`              int(11)      NOT NULL DEFAULT '0' COMMENT '重试次数',
    `cause`              text COMMENT '失败原因',
    `retry_count`        int(11)               DEFAULT NULL COMMENT '消息队列中间件的重试次数',
    `effect_time`        datetime              DEFAULT CURRENT_TIMESTAMP COMMENT '生效时间',
    `create_date`        datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    `last_modified_date` datetime              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日期',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_group_topic_code` (`topic`, `code`, `msg_group`) USING BTREE,
    KEY `idx_status` (`status`),
    KEY `idx_code` (`code`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1 COMMENT ='发送消息表';

CREATE TABLE `mq_broker_msg_receive`
(
    `id`                 bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `msg_group`          varchar(255)          DEFAULT NULL COMMENT '分组',
    `topic`              varchar(255) NOT NULL COMMENT '队列名',
    `code`               varchar(255) NOT NULL COMMENT '代码',
    `data`               text         NOT NULL COMMENT '内容',
    `status`             int(11)      NOT NULL COMMENT '状态 0:待消费 1:正在消费 2:发送成功 3:发送失败',
    `host_address`       varchar(255)          DEFAULT NULL COMMENT '主机地址',
    `retry`              int(11)      NOT NULL DEFAULT '0' COMMENT '重试次数',
    `cause`              text COMMENT '失败原因',
    `create_date`        datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    `last_modified_date` datetime              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日期',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_group_topic_code` (`topic`, `code`, `msg_group`) USING BTREE,
    KEY `idx_status` (`status`),
    KEY `idx_code` (`code`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1 COMMENT ='接收消息表';