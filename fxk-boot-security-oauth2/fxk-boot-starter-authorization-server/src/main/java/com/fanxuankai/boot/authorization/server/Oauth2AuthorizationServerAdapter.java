package com.fanxuankai.boot.authorization.server;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;
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
    @Resource
    private AuthenticationManager authenticationManager;
    /**
     * 数据源
     */
    @Resource
    private DataSource dataSource;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private TokenStore tokenStore;
    @Resource
    private AccessTokenConverter accessTokenConverter;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        // 配置 token 端点的安全约束
        security
                // oauth/token_key 公开
                .tokenKeyAccess("permitAll()")
                // oauth/check_token 公开
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 配置客户端详情服务,可以配置在内存也可以配置在数据库
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // 开启密码授权类型
        endpoints.authenticationManager(authenticationManager)
                // 配置token存储方式，一共有五种
                .tokenStore(tokenStore)
                // 对Jwt签名时，增加一个密钥，JwtAccessTokenConverter：对Jwt来进行编码以及解码的类
                .accessTokenConverter(accessTokenConverter)
                // 鉴权失败时的返回信息
                .exceptionTranslator(new Oauth2ExceptionTranslator())
                // 要使用refresh_token的话，需要额外配置userDetailsService
                .userDetailsService(userDetailsService);
    }
}
