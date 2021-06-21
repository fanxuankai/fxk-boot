package com.fanxuankai.boot.admin.autoconfigure;

import com.fanxuankai.boot.admin.service.UserService;
import com.fanxuankai.boot.admin.service.impl.UserServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author fanxuankai
 */
@ComponentScan(basePackages = {"com.fanxuankai.boot.admin"})
@MapperScan(basePackages = {"com.fanxuankai.boot.admin.mapper"})
@EnableConfigurationProperties({AdminProperties.class})
public class AdminConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean
    public UserService userService() {
        return new UserServiceImpl();
    }
}
