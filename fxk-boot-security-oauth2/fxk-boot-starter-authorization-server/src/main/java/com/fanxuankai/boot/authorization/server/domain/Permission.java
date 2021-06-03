package com.fanxuankai.boot.authorization.server.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author fanxuankai
 */
public class Permission implements GrantedAuthority {
    private Long id;
    private String url;
    private String name;
    private String description;
    private Long pid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
