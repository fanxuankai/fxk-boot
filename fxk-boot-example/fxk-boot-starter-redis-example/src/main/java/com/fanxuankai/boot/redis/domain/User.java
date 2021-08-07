package com.fanxuankai.boot.redis.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author fanxuankai
 */
@Data
@Accessors(chain = true)
public class User {
    private String name;
    private String pass;
    private int age;
}
