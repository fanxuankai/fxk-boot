package com.fanxuankai.boot.canal.redis.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author fanxuankai
 */
@Data
@Accessors(chain = true)
public class CombineKeyModel {
    private final List<Entry> entries;
}
