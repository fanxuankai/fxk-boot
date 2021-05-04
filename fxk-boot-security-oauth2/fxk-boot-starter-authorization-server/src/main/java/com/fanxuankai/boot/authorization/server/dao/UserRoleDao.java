package com.fanxuankai.boot.authorization.server.dao;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fanxuankai.boot.authorization.server.domain.UserRole;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
public interface UserRoleDao extends IService<UserRole> {
    /**
     * 查用户所有角色 id
     *
     * @param userId 用户 id
     * @return /
     */
    default List<Long> listRoleIds(Long userId) {
        return list(Wrappers.lambdaQuery(UserRole.class).eq(UserRole::getUserId, userId))
                .stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
    }
}
