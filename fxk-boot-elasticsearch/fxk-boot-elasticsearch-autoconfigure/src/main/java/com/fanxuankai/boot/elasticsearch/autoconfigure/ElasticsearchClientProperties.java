package com.fanxuankai.boot.elasticsearch.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * RestHighLevelClient 配置
 *
 * @author fanxuankai
 */
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticsearchClientProperties {
    /**
     * ip:端口, 多个以逗号隔开, 端口为 http 端口
     */
    private List<String> hostAndPorts;
    /**
     * 用户
     */
    private String user;
    /**
     * 密码
     */
    private String password;
    /**
     * 连接超时
     */
    private Long connectTimeout;
    /**
     * socket 超时 ms
     */
    private Long socketTimeout;

    public List<String> getHostAndPorts() {
        return hostAndPorts;
    }

    public void setHostAndPorts(List<String> hostAndPorts) {
        this.hostAndPorts = hostAndPorts;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Long getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Long socketTimeout) {
        this.socketTimeout = socketTimeout;
    }
}
