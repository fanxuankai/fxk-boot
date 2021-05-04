package com.fanxuankai.boot.authorization.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fanxuankai.boot.authorization.server.domain.Permission;
import com.fanxuankai.boot.authorization.server.vo.RolePermissionVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author fanxuankai
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 获取所有角色以及角色的权限
     *
     * @return List
     */
    @Select("SELECT r.id roleId, r.name roleName, p.url permissionUrl, p.name permissionName, p.description " +
            "permissionDescription FROM role r LEFT JOIN role_permission rp ON r.id = rp.role_id " +
            "LEFT JOIN permission p ON rp.permission_id = p.id")
    List<RolePermissionVO> getRolePermissions();
}
