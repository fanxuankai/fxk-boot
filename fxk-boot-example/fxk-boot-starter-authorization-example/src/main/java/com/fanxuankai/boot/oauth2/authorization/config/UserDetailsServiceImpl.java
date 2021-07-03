package com.fanxuankai.boot.oauth2.authorization.config;

import cn.hutool.core.collection.ListUtil;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Map<String, UserDetails> USERS;

    static {
        // 这里是演示，正式应该从库里查
        USERS = ListUtil.toList(User.withUsername("admin")
                .password("$2a$10$dmBN2ZC0MEtb1rIarY.aO.OAE0jReVw0yu04FmQ1iw5Km.p6HTz8y")
                .authorities("user")
                .build())
                .stream()
                .collect(Collectors.toMap(UserDetails::getUsername, Function.identity()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = USERS.get(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("无效用户名");
        }
        // 这里需要创建一个新对象，因为鉴权失败时会被修改
        return User.withUserDetails(userDetails).build();
    }
}
