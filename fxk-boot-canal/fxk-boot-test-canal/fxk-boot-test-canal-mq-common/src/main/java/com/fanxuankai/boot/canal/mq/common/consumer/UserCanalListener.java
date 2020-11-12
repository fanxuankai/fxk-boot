package com.fanxuankai.boot.canal.mq.common.consumer;

import com.fanxuankai.boot.canal.mq.common.domain.User;
import com.fanxuankai.canal.mq.core.annotation.CanalListener;
import com.fanxuankai.canal.mq.core.annotation.Delete;
import com.fanxuankai.canal.mq.core.annotation.Insert;
import com.fanxuankai.canal.mq.core.annotation.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author fanxuankai
 */
@Service
@CanalListener(entityClass = User.class, waitRateSeconds = 1, waitMaxSeconds = 1)
public class UserCanalListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserCanalListener.class);

    @Update
    public void update(User before, User after) {
        LOGGER.info("update {}", before.getId());
    }

    @Insert
    public void insert(User user) {
        LOGGER.info("insert {}", user.getId());
    }

    @Delete
    public void delete(User user) {
        LOGGER.info("delete {}", user.getId());
    }
}
