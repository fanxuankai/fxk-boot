CREATE TABLE `mq_broker_msg_send`
(
    `id`                 bigint(20)                                             NOT NULL AUTO_INCREMENT COMMENT '主键',
    `topic`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '队列名',
    `code`               varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '代码',
    `data`               text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin         NOT NULL COMMENT '内容',
    `status`             int(11)                                                NOT NULL COMMENT '状态 0:已创建 1:运行中 2:成功 3:失败',
    `host_address`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin          DEFAULT NULL COMMENT '主机地址',
    `retry`              int(11)                                                NOT NULL DEFAULT '0' COMMENT '重试次数',
    `cause`              text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '失败原因',
    `create_date`        datetime                                               NOT NULL COMMENT '创建日期',
    `last_modified_date` datetime                                               NOT NULL COMMENT '修改日期',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_topic_code` (`topic`, `code`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='发送消息表';

CREATE TABLE `mq_broker_msg_receive`
(
    `id`                 bigint(20)                                             NOT NULL AUTO_INCREMENT COMMENT '主键',
    `topic`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '队列名',
    `code`               varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '代码',
    `data`               text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin         NOT NULL COMMENT '内容',
    `status`             int(11)                                                NOT NULL COMMENT '状态 0:已创建 1:运行中 2:成功 3:失败',
    `host_address`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin          DEFAULT NULL COMMENT '主机地址',
    `retry`              int(11)                                                NOT NULL DEFAULT '0' COMMENT '重试次数',
    `cause`              text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '失败原因',
    `create_date`        datetime                                               NOT NULL COMMENT '创建日期',
    `last_modified_date` datetime                                               NOT NULL COMMENT '修改日期',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_topic_code` (`topic`, `code`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='接收消息表';