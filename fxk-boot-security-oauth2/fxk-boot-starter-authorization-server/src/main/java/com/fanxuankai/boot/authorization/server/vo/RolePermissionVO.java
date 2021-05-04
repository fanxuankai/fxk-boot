package com.fanxuankai.boot.authorization.server.vo;

/**
 * @author fanxuankai
 */
public class RolePermissionVO {
    private Long roleId;
    private String roleName;
    private String permissionUrl;
    private String permissionName;
    private String permissionDescription;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPermissionUrl() {
        return permissionUrl;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionDescription() {
        return permissionDescription;
    }

    public void setPermissionDescription(String permissionDescription) {
        this.permissionDescription = permissionDescription;
    }

    @Override
    public String toString() {
        return "RolePermissionVO{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", permissionUrl='" + permissionUrl + '\'' +
                ", permissionName='" + permissionName + '\'' +
                ", permissionDescription='" + permissionDescription + '\'' +
                '}';
    }
}
