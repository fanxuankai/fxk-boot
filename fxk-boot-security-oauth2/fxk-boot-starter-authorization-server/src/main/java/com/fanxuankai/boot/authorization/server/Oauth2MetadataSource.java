package com.fanxuankai.boot.authorization.server;


import com.fanxuankai.boot.authorization.server.mapper.PermissionMapper;
import com.fanxuankai.boot.authorization.server.vo.RolePermissionVO;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
public class Oauth2MetadataSource implements FilterInvocationSecurityMetadataSource {
    private final PermissionMapper permissionMapper;

    public Oauth2MetadataSource(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    /**
     * 每一个资源所需要的角色 Collection<ConfigAttribute>决策器会用到
     */
    private static Map<String, Collection<ConfigAttribute>> map = Collections.emptyMap();

    /**
     * 返回请求的资源需要的角色
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();
        for (String url : map.keySet()) {
            if (new AntPathRequestMatcher(url).matches(request)) {
                return map.get(url);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        // 初始化 所有资源 对应的角色
        loadResourceDefine();
        return map.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    /**
     * 初始化 所有资源 对应的角色
     */
    public void loadResourceDefine() {
        map = new HashMap<>(16);
        List<RolePermissionVO> rolePermissions = permissionMapper.getRolePermissions();
        for (RolePermissionVO rolePermissionVO : rolePermissions) {
            String url = rolePermissionVO.getPermissionUrl();
            String roleName = rolePermissionVO.getRoleName();
            ConfigAttribute role = new SecurityConfig(roleName);
            if (map.containsKey(url)) {
                map.get(url).add(role);
            } else {
                List<ConfigAttribute> list = new ArrayList<>();
                list.add(role);
                map.put(url, list);
            }
        }
    }
}
