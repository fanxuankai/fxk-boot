package com.fanxuankai.boot.log.service;

import com.fanxuankai.boot.log.domain.User;
import com.fanxuankai.commons.util.IdUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author fanxuankai
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    public void test() {
        User user = new User()
                .setId(IdUtils.nextId())
                .setUsername(UUID.randomUUID().toString())
                .setPassword(UUID.randomUUID().toString());
        userService.save(user);
    }
}
