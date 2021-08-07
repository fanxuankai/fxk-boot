package com.fanxuankai.boot.log.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author fanxuankai
 */
@Data
@Accessors(chain = true)
public class User {
    private Long id;
    private String username;
    private String password;
}
