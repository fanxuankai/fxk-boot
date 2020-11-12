package com.fanxuankai.boot.mqbroker.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 事件
 *
 * @author fanxuankai
 */
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public Date getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(Date effectTime) {
        this.effectTime = effectTime;
    }
}
