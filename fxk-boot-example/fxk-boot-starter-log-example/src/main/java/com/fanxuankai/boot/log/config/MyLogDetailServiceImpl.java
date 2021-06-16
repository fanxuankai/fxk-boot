package com.fanxuankai.boot.log.config;

import com.fanxuankai.boot.log.LogDetailService;
import org.springframework.stereotype.Component;

/**
 * 自定义 LogDetailService
 *
 * @author fanxuankai
 */
@Component
public class MyLogDetailServiceImpl implements LogDetailService {
    @Override
    public String getUsername() {
        return "Micheal Jackson";
    }
}
