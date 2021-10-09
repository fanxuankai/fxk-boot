package com.fanxuankai.boot.mqbroker.enums;

/**
 * @author fanxuankai
 */
public enum ReceiveStatus {
    /**
     * 待消费
     */
    WAIT(0),
    /**
     * 正在消费
     */
    CONSUMING(1),
    /**
     * 消费成功
     */
    SUCCESS(2),
    /**
     * 消费失败
     */
    FAILURE(3),
    ;
    private final int code;

    ReceiveStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
