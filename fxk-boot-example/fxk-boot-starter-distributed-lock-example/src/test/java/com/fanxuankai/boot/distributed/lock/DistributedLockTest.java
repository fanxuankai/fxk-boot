package com.fanxuankai.boot.distributed.lock;

import com.fanxuankai.boot.distributed.lock.domian.User;
import com.fanxuankai.boot.distributed.lock.service.UserService;
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
public class DistributedLockTest {
    @Resource
    private DistributedLocker distributedLocker;
    @Resource
    private UserService userService;

    @Test
    public void testLock() {
        distributedLocker.lock("lock", () -> System.out.println("do in lock"));
        distributedLocker.lock("我的业务", Arrays.asList(1, 2, 3), 5000, 60000, () -> System.out.println(1));
    }

    @Test
    public void testLockWithAnnotation() {
        int nThreads = 50;
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
