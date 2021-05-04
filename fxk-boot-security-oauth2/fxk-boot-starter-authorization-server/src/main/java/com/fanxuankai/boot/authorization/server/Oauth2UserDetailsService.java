package com.fanxuankai.boot.authorization.server;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fanxuankai.boot.authorization.server.dao.RoleDao;
import com.fanxuankai.boot.authorization.server.dao.UserDao;
import com.fanxuankai.boot.authorization.server.dao.UserRoleDao;
import com.fanxuankai.boot.authorization.server.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author fanxuankai
 */
public class Oauth2UserDetailsService implements UserDetailsService {
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final UserRoleDao userRoleDao;

    public Oauth2UserDetailsService(UserDao userDao, RoleDao roleDao, UserRoleDao userRoleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.userRoleDao = userRoleDao;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDao.getOne(Wrappers.lambdaQuery(User.class).eq(User::getUsername, userName));
        if (user == null) {
            return null;
        }
        List<Long> roleIds = userRoleDao.listRoleIds(user.getId());
        if (!CollectionUtils.isEmpty(roleIds)) {
            user.setAuthorities(roleDao.listByIds(roleIds));
        }
        return user;
    }
}
