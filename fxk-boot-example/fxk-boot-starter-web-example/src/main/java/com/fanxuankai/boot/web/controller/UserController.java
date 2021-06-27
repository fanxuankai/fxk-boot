package com.fanxuankai.boot.web.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import com.fanxuankai.boot.web.domain.User;
import com.fanxuankai.commons.domain.Result;
import com.fanxuankai.commons.exception.LockException;
import com.fanxuankai.commons.util.IdUtils;
import com.fanxuankai.commons.util.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fanxuankai
 */
@RestController
public class UserController {
    @GetMapping("map")
    public Result<Object> map() {
        if (RandomUtil.randomInt() % 2 == 0) {
            throw new LockException();
        }
        return ResultUtils.ok(MapUtil.builder("sal", 500_000_000L).put("age", null).build());
    }

    @GetMapping("user")
    public Result<User> user() {
        return ResultUtils.ok(new User().setId(IdUtils.nextId()).setName("fanxuankai"));
    }
}
