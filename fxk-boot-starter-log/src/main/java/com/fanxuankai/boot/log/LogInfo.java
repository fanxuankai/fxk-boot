package com.fanxuankai.boot.log;

import java.util.Date;

/**
 * 日志信息
 *
 * @author fanxuankai
 */
public class LogInfo {
    private Long id;
    /**
     * 操作用户
     */
    private String username;

    /**
     * 资源名称
     */
    private String resource;

    /**
     * 统一资源标识符
     */
    private String uri;

    /**
     * 安全等级(0: 普通 1: 中等 2: 高风险)
     */
    private Integer safetyLevel;

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 参数
     */
    private String params;

    /**
     * 返回值
     */
    private String returnValue;

    /**
     * 服务器 IP
     */
    private String serverIp;

    /**
     * 客户端 IP
     */
    private String clientIp;

    /**
     * 客户端地址
     */
    private String clientAddress;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 总时间毫秒数
     */
    private Long totalTimeMillis;

    /**
     * 操作是否异常
     */
    private Boolean operationException;

    /**
     * 异常详细
     */
    private String exceptionDetail;

    /**
     * 创建日期
     */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getSafetyLevel() {
        return safetyLevel;
    }

    public void setSafetyLevel(Integer safetyLevel) {
        this.safetyLevel = safetyLevel;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public Long getTotalTimeMillis() {
        return totalTimeMillis;
    }

    public void setTotalTimeMillis(Long totalTimeMillis) {
        this.totalTimeMillis = totalTimeMillis;
    }

    public Boolean getOperationException() {
        return operationException;
    }

    public void setOperationException(Boolean operationException) {
        this.operationException = operationException;
    }

    public String getExceptionDetail() {
        return exceptionDetail;
    }

    public void setExceptionDetail(String exceptionDetail) {
        this.exceptionDetail = exceptionDetail;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", resource='" + resource + '\'' +
                ", uri='" + uri + '\'' +
                ", safetyLevel=" + safetyLevel +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", params='" + params + '\'' +
                ", returnValue='" + returnValue + '\'' +
                ", serverIp='" + serverIp + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", clientAddress='" + clientAddress + '\'' +
                ", browser='" + browser + '\'' +
                ", totalTimeMillis=" + totalTimeMillis +
                ", operationException=" + operationException +
                ", exceptionDetail='" + exceptionDetail + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}