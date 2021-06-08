package com.fanxuankai.boot.authorization.server.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Objects;

/**
 * 认证服务器配置
 *
 * @author fanxuankai
 */
@Configuration
@Import({LogoutEndpoint.class})
public class AuthorizationServerAutoConfiguration {
    /**
     * 认证服务器配置
     *
     * @author fanxuankai
     */
    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
        @Resource
        private AuthenticationManager authenticationManager;
        @Resource
        private DataSource dataSource;
        @Resource
        private UserDetailsService userDetailsService;
        @Resource
        private TokenStore tokenStore;
        @Resource
        private AccessTokenConverter accessTokenConverter;
        @Resource
        private WebResponseExceptionTranslator<OAuth2Exception> webResponseExceptionTranslator;

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
                    // 配置 token 存储方式
                    .tokenStore(tokenStore)
                    // 对 Jwt 签名时，增加一个密钥
                    .accessTokenConverter(accessTokenConverter)
                    // 鉴权失败时的返回信息
                    .exceptionTranslator(webResponseExceptionTranslator)
                    // 要使用 refresh_token 的话，需要额外配置 userDetailsService
                    .userDetailsService(userDetailsService);
        }

        @Bean
        @ConditionalOnMissingBean
        public WebResponseExceptionTranslator<OAuth2Exception> webResponseExceptionTranslator() {
            return new WebExceptionTranslator();
        }
    }

    @Configuration
    @EnableWebSecurity
    protected static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Resource
        private UserDetailsService userDetailsService;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            // 校验用户
            auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {
                // 对密码进行加密
                @Override
                public String encode(CharSequence charSequence) {
                    return DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
                }

                // 对密码进行判断匹配
                @Override
                public boolean matches(CharSequence charSequence, String s) {
                    String encode = DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
                    return s.equals(encode);
                }
            });
        }

        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManager();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/oauth/**", "/login", "/logout", "/login-error").permitAll()
                    .and()
                    .formLogin();
        }

        @Bean
        @ConditionalOnMissingBean
        public PasswordEncoder passwordEncoder() {
            return new PasswordEncoder() {
                @Override
                public String encode(CharSequence charSequence) {
                    return charSequence.toString();
                }

                @Override
                public boolean matches(CharSequence charSequence, String s) {
                    return Objects.equals(charSequence.toString(), s);
                }
            };
        }
    }

    static class JdbcTokenServiceConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public TokenStore tokenStore(DataSource dataSource) {
            return new JdbcTokenStore(dataSource);
        }
    }

    static class RedisTokenServiceConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory) {
            return new RedisTokenStore(redisConnectionFactory);
        }
    }

    /**
     * 自定义登录或者鉴权失败时的返回信息
     */
    static class WebExceptionTranslator extends DefaultWebResponseExceptionTranslator {
        @Override
        public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
            ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
            OAuth2Exception body = responseEntity.getBody();
            HttpHeaders headers = new HttpHeaders();
            headers.setAll(responseEntity.getHeaders().toSingleValueMap());
            return new ResponseEntity<>(body, headers, responseEntity.getStatusCode());
        }
    }
}
