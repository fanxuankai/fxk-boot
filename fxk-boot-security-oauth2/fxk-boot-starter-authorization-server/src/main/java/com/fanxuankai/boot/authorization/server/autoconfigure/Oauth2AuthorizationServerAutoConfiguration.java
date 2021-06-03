package com.fanxuankai.boot.authorization.server.autoconfigure;

import com.fanxuankai.boot.authorization.server.*;
import com.fanxuankai.boot.authorization.server.dao.UserDao;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

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
    public AuthorizationServerConfigurer authorizationServerConfigurer() {
        // 配置 token 的访问端点和 token 服务
        return new Oauth2AuthorizationServerAdapter();
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("test-secret");
        return converter;
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
    public SecurityMetadataSource securityMetadataSource() {
        return new Oauth2MetadataSource();
    }

    @Bean
    @ConditionalOnMissingBean
    public UserDetailsService userDetailsService() {
        return new Oauth2UserDetailsServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public AbstractSecurityInterceptor securityInterceptor(AccessDecisionManager accessDecisionManager) {
        Oauth2SecurityInterceptor oauth2SecurityInterceptor = new Oauth2SecurityInterceptor();
        oauth2SecurityInterceptor.setAccessDecisionManager(accessDecisionManager);
        return oauth2SecurityInterceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
//        return new JdbcTokenStore(dataSource);
        // 资源服务器本地验证需要使用 JwtTokenStore
        return new JwtTokenStore(jwtAccessTokenConverter);
    }
}
