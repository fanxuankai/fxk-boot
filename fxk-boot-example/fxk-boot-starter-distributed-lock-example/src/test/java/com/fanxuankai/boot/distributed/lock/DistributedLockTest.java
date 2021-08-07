package com.fanxuankai.boot.distributed.lock;

import com.fanxuankai.boot.distributed.lock.domian.User;
import com.fanxuankai.boot.distributed.lock.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author fanxuankai
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DistributedLockTest {
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
