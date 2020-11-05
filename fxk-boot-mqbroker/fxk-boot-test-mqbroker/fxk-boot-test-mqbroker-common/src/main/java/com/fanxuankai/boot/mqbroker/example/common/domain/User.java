package com.fanxuankai.boot.mqbroker.example.common.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author fanxuankai
 */
@Data
@Accessors(chain = true)
public class User {
    private Long id;
    private String name;
    private String age;
}
