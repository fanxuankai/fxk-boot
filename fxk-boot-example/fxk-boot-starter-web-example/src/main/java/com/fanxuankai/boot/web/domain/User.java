package com.fanxuankai.boot.web.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author fanxuankai
 */
@Data
@Accessors(chain = true)
public class User {
    private Long id;
    private String name;
    private String pass;
    private List<String> interests;
}
