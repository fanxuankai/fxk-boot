package com.fanxuankai.boot.log.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fanxuankai
 */
@RestController
public class GreetingController {
    @GetMapping("hi")
    public String hi(String username) {
        return "Hi, " + username;
    }
}