package com.fanxuankai.boot.authorization.server.config;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fanxuankai.boot.admin.dao.UserDao;
import com.fanxuankai.boot.admin.mapper.MenuMapper;
import com.fanxuankai.boot.admin.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserDao userDao;
    @Resource
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDao.getOne(Wrappers.lambdaQuery(User.class)
                .eq(User::getUsername, userName));
        if (user == null) {
            return null;
        }
        List<String> permissions = menuMapper.listPermissions(user.getId());
        List<SimpleGrantedAuthority> authorityList =
                permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorityList)
                .accountExpired(user.getAccountExpired())
                .accountLocked(user.getAccountLocked())
                .credentialsExpired(user.getCredentialsExpired())
                .build();
    }
}
