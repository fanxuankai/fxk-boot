package com.fanxuankai.boot.log.service.impl;

import com.fanxuankai.boot.log.annotation.Log;
import com.fanxuankai.boot.log.domain.User;
import com.fanxuankai.boot.log.enums.SafetyLevel;
import com.fanxuankai.boot.log.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author fanxuankai
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    @Log(value = "保存用户", safetyLevel = SafetyLevel.SECONDARY)
    public void save(User user) {

    }
}
