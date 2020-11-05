package com.fanxuankai.boot.mqbroker.produce;

import java.util.List;

/**
 * 发布者
 *
 * @author fanxuankai
 */
public interface Publisher<T> {

    /**
     * 发布单个
     *
     * @param t item
     */
    void publish(T t);

    /**
     * 发布多个
     *
     * @param list items
     */
    void publish(List<T> list);

    /**
     * 发布单个
     *
     * @param t     item
     * @param async 异步
     */
    void publish(T t, boolean async);

    /**
     * 发布多个
     *
     * @param list  items
     * @param async 异步
     */
    void publish(List<T> list, boolean async);
}
