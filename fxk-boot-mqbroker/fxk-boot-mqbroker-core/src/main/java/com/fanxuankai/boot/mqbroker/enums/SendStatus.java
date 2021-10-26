package com.fanxuankai.boot.mqbroker.enums;

/**
 * @author fanxuankai
 */
public enum SendStatus {
    /**
     * 延迟发送
     */
    DELAYED(-1),
    /**
     * 待发送
     */
    WAIT(0),
    /**
     * 正在发送
     */
    SENDING(1),
    /**
     * 发送成功
     */
    SUCCESS(2),
    /**
     * 发送失败
     */
    FAILURE(3),
    ;
    private final int code;

    SendStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
