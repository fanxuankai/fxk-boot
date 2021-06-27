package com.fanxuankai.boot.web.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author fanxuankai
 */
@ConfigurationProperties(prefix = WebProperties.PREFIX)
public class WebProperties {
    public static final String PREFIX = "fxk.web";

    /**
     * long 转字符串
     */
    private boolean longToString = true;
    /**
     * null 转 empty
     */
    private boolean nullToEmpty = true;
    /**
     * 开启全局异常处理
     */
    private boolean enableExceptionAdvice = true;

    public boolean isLongToString() {
        return longToString;
    }

    public void setLongToString(boolean longToString) {
        this.longToString = longToString;
    }

    public boolean isNullToEmpty() {
        return nullToEmpty;
    }

    public void setNullToEmpty(boolean nullToEmpty) {
        this.nullToEmpty = nullToEmpty;
    }

    public boolean isEnableExceptionAdvice() {
        return enableExceptionAdvice;
    }

    public void setEnableExceptionAdvice(boolean enableExceptionAdvice) {
        this.enableExceptionAdvice = enableExceptionAdvice;
    }
}
