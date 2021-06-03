package com.fanxuankai.boot.resource.server.autoconfigure;

import com.fanxuankai.boot.resource.server.Oauth2ResourceServerConfigurerAdapter;
import com.fanxuankai.boot.resource.server.Oauth2WebSecurityConfigurerAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author fanxuankai
 */
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class Oauth2ResourceServerAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ResourceServerConfigurerAdapter resourceServerConfigurerAdapter() {
        return new Oauth2ResourceServerConfigurerAdapter();
    }

    @Bean
    @ConditionalOnMissingBean
    public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
        return new Oauth2WebSecurityConfigurerAdapter();
    }

    @Bean
    @ConditionalOnMissingBean
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        // 本地 jwt 验证
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("test-secret");
        return converter;
    }
}
