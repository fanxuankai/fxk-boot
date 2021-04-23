package com.fanxuankai.boot.canal.rabbit;

import com.fanxuankai.boot.canal.mq.common.domain.User;
import com.fanxuankai.boot.canal.mq.common.service.UserService;
import com.fanxuankai.commons.core.util.MockUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {
    @Resource
    private UserService userService;

    @Test
    public void saveAll() {
        userService.saveBatch(MockUtils.mock(0, 100000, User.class)
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
