package com.fanxuankai.boot.authorization.server.autoconfigure;

import com.fanxuankai.boot.authorization.server.*;
import com.fanxuankai.boot.authorization.server.dao.RoleDao;
import com.fanxuankai.boot.authorization.server.dao.UserDao;
import com.fanxuankai.boot.authorization.server.dao.UserRoleDao;
import com.fanxuankai.boot.authorization.server.mapper.PermissionMapper;
import com.fanxuankai.boot.authorization.server.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * 认证服务器配置
 *
 * @author fanxuankai
 */
@Configuration
@EnableAuthorizationServer
@MapperScan(basePackageClasses = {UserMapper.class})
@ComponentScan(basePackageClasses = {UserDao.class})
@Import({Oauth2WebSecurityAutoConfiguration.class})
public class Oauth2AuthorizationServerAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public AuthorizationServerConfigurer authorizationServerConfigurer(AuthenticationManager authenticationManager,
                                                                       DataSource dataSource,
                                                                       UserDetailsService userDetailsService,
                                                                       TokenStore tokenStore) {
        return new Oauth2AuthorizationServerAdapter(authenticationManager, dataSource, userDetailsService, tokenStore);
    }

    @Bean
    @ConditionalOnMissingBean
    public AccessDecisionManager accessDecisionManager() {
        return new Oauth2AccessDecisionManager();
    }

    @Bean
    @ConditionalOnMissingBean
    public WebResponseExceptionTranslator<OAuth2Exception> webResponseExceptionTranslator() {
        return new Oauth2ExceptionTranslator();
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityMetadataSource securityMetadataSource(PermissionMapper permissionMapper) {
        return new Oauth2MetadataSource(permissionMapper);
    }

    @Bean
    @ConditionalOnMissingBean
    public UserDetailsService userDetailsService(UserDao userDao, RoleDao roleDao, UserRoleDao userRoleDao) {
        return new Oauth2UserDetailsServiceImpl(userDao, roleDao, userRoleDao);
    }

    @Bean
    @ConditionalOnMissingBean
    public AbstractSecurityInterceptor securityInterceptor(SecurityMetadataSource securityMetadataSource,
                                                           AccessDecisionManager accessDecisionManager) {
        return new Oauth2SecurityInterceptor(securityMetadataSource, accessDecisionManager);
    }

    @Bean
    @ConditionalOnMissingBean
    public TokenStore tokenStore(DataSource dataSource) {
        return new JdbcTokenStore(dataSource);
    }
}
