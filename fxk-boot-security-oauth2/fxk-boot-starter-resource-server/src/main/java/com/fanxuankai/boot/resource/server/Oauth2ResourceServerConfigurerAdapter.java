package com.fanxuankai.boot.resource.server;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;

/**
 * 资源服务器
 *
 * @author fanxuankai
 */
public class Oauth2ResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {
    @Resource
    private TokenStore tokenStore;

    /**
     * 这里设置需要token验证的url
     * 这些url可以在WebSecurityConfigurerAdapter中排查掉，
     * 对于相同的url，如果二者都配置了验证
     * 则优先进入ResourceServerConfigurerAdapter,进行token验证。而不会进行
     * WebSecurityConfigurerAdapter 的 basic auth或表单认证。
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("order")
                .tokenStore(tokenStore)
                .stateless(true);
    }

    private ResourceServerTokenServices resourceServerTokenServices() {
        // 远程 token 验证
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setCheckTokenEndpointUrl("http://localhost:8080/oauth/check_token");
        tokenServices.setClientId("dev");
        tokenServices.setClientSecret("dev");
        return tokenServices;
    }
}
