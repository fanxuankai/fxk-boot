package com.fanxuankai.boot.resource.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fanxuankai
 */
@RestController("/api")
public class HelloController {
    @PostMapping("/api/hi")
    @PreAuthorize("hasAuthority('user')")
    public String say(String name) {
        return "hi , " + name;
    }
}
