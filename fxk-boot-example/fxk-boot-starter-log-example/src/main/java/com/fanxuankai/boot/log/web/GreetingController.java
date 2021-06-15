package com.fanxuankai.boot.log.web;

import com.fanxuankai.boot.log.annotation.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fanxuankai
 */
@RestController
public class GreetingController {
    @Log("打招呼")
    @GetMapping
    public String hi(String username) {
        return "Hi, " + username;
    }
}