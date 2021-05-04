package com.fanxuankai.boot.authorization.server;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;

/**
 * 认证服务器配置
 *
 * @author fanxuankai
 */
public class Oauth2AuthorizationServerAdapter extends AuthorizationServerConfigurerAdapter {
    /**
     * 注入权限验证控制器 来支持 password grant type
     */
    private final AuthenticationManager authenticationManager;
    /**
     * 数据源
     */
    private final DataSource dataSource;
    private final UserDetailsService userDetailsService;

    public Oauth2AuthorizationServerAdapter(AuthenticationManager authenticationManager,
                                            DataSource dataSource,
                                            UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.dataSource = dataSource;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("test-secret");
        // 开启密码授权类型
        endpoints.authenticationManager(authenticationManager)
                // 配置token存储方式，一共有五种，这里采用数据库的方式
                .tokenStore(new JdbcTokenStore(dataSource))
                // 对Jwt签名时，增加一个密钥，JwtAccessTokenConverter：对Jwt来进行编码以及解码的类
                .accessTokenConverter(converter)
                // 鉴权失败时的返回信息
                .exceptionTranslator(new Oauth2ExceptionTranslator())
                // 要使用refresh_token的话，需要额外配置userDetailsService
                .userDetailsService(userDetailsService);
    }
}
