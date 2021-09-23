package com.fanxuankai.boot.mp.mapper;

import com.fanxuankai.boot.mp.domain.User;
import com.fanxuankai.commons.util.MockUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fanxuankai
 */
@SpringBootTest
public class UserMapperTest {
    @Resource
    private UserMapper userMapper;

    @Test
    public void insertBatch() {
        List<User> userList = MockUtils.mock(0, 100, User.class);
        userMapper.insertBatchSomeColumn(userList);
    }
}
