package com.fanxuankai.boot.mqbroker.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 事件
 *
 * @author fanxuankai
 */
@Data
@Accessors(chain = true)
public class Event<T> implements Serializable {
    /**
     * 分组, 可选
     */
    private String group;
    /**
     * 事件名
     */
    private String name;
    /**
     * key
     */
    private String key;
    /**
     * 数据
     */
    private T data;
    /**
     * 重试次数
     */
    private Integer retryCount;
    /**
     * 生效时间
     */
    private Date effectTime;
}
