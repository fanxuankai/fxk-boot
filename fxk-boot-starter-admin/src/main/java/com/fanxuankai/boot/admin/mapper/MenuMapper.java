package com.fanxuankai.boot.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fanxuankai.boot.admin.model.Menu;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 菜单 Mapper 接口
 *
 * @author fanxuankai
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 获取用户的权限
     *
     * @param userId 用户id
     * @return List
     */
    @Select("select m.permission from menu m " +
            "LEFT JOIN role_menu rp on rp.menu_id = m.id " +
            "LEFT JOIN role r on r.id = rp.role_id " +
            "LEFT JOIN user_role ur on ur.role_id = r.id " +
            "LEFT JOIN user u on u.id = ur.user_id " +
            "WHERE u.id = #{userId} AND u.deleted = 0 " +
            "AND r.deleted = 0 AND m.deleted = 0")
    List<String> listPermissions(Long userId);
}