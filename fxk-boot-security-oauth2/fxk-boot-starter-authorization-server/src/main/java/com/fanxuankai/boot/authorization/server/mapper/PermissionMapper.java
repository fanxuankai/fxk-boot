package com.fanxuankai.boot.authorization.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fanxuankai.boot.authorization.server.domain.Permission;
import com.fanxuankai.boot.authorization.server.vo.RolePermissionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author fanxuankai
 */
@Mapper
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

    /**
     * 获取用户的权限
     *
     * @param userId 用户id
     * @return List
     */
    @Select("select p.* from permission p " +
            "LEFT JOIN role_permission rp on rp.permission_id = p.id " +
            "LEFT JOIN role r on r.id = rp.role_id " +
            "LEFT JOIN user_role ur on ur.role_id = r.id " +
            "LEFT JOIN user u on u.id = ur.user_id " +
            "WHERE u.id = #{userId}")
    List<Permission> getRolePermissionsByUserId(Long userId);
}
