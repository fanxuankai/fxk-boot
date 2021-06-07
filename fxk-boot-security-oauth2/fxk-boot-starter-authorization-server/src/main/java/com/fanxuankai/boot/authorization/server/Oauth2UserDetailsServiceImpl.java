//package com.fanxuankai.boot.authorization.server;
//
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.fanxuankai.boot.authorization.server.dao.UserDao;
//import com.fanxuankai.boot.authorization.server.domain.Permission;
//import com.fanxuankai.boot.authorization.server.domain.User;
//import com.fanxuankai.boot.authorization.server.mapper.PermissionMapper;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * @author fanxuankai
// */
//public class Oauth2UserDetailsServiceImpl implements UserDetailsService {
//    @Resource
//    private UserDao userDao;
//    @Resource
//    private PermissionMapper permissionMapper;
//
//    @Override
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        User user = userDao.getOne(Wrappers.lambdaQuery(User.class).eq(User::getUsername, userName));
//        if (user == null) {
//            return null;
//        }
//        List<Permission> permissions = permissionMapper.getRolePermissionsByUserId(user.getId());
//        user.setAuthorities(permissions);
//        return user;
//    }
//}
