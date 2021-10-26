package com.fanxuankai.boot.canal.kafka;

import com.fanxuankai.boot.canal.mq.common.domain.User;
import com.fanxuankai.boot.canal.mq.common.mapper.UserMapper;
import com.fanxuankai.commons.util.MockUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
@SpringBootTest
public class UserRepositoryTest {
    @Resource
    private UserMapper userMapper;

    @Test
    public void saveAll() {
        userMapper.insertBatchSomeColumn(MockUtils.mock(0, 100, User.class)
                .stream()
                .peek(user -> {
                    user.setId(null);
                    user.setDeleted(false);
                    user.setCreateDate(null);
                    user.setLastModifiedDate(null);
                })
                .collect(Collectors.toList()));
    }
}
