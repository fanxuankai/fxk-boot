package com.fanxuankai.boot.distributed.lock.service.impl;

import com.fanxuankai.boot.distributed.lock.annotation.Lock;
import com.fanxuankai.boot.distributed.lock.domian.User;
import com.fanxuankai.boot.distributed.lock.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author fanxuankai
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    @Lock(resources = {"#user.username", "#user.password"})
    public void login(User user) {
        System.out.println(Thread.currentThread() + new Date().toString());
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
