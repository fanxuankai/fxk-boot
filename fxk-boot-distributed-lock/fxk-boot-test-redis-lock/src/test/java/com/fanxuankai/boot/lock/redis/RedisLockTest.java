package com.fanxuankai.boot.lock.redis;

import com.fanxuankai.boot.distributed.lock.DistributedLocker;
import com.fanxuankai.boot.lock.redis.domian.User;
import com.fanxuankai.boot.lock.redis.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author fanxuankai
 */
@SpringBootTest
public class RedisLockTest {

    @Resource
    private DistributedLocker distributedLocker;
    @Resource
    private UserService userService;

    @Test
    public void testLock() {
        distributedLocker.lock("lock", () -> System.out.println("do in lock"));
        distributedLocker.lock(() -> System.out.println(1), "我的业务", 5000, 60000, Arrays.asList(1, 2, 3));
    }

    @Test
    public void testLockWithAnnotation() {
        int nThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        User user = new User();
        user.setUsername("fxk");
        user.setPassword("123");
        for (int i = 0; i < nThreads; i++) {
            executorService.execute(() -> userService.login(user));
        }
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
