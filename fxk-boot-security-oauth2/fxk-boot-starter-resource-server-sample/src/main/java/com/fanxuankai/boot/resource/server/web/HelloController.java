package com.fanxuankai.boot.resource.server.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fanxuankai
 */
@RestController("/api")
public class HelloController {
    @PostMapping("/api/hi")
    public String say(String name) {
        return "hi , " + name;
    }
}
