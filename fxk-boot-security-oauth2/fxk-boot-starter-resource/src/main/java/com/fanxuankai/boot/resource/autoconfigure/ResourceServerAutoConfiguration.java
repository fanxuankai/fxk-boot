package com.fanxuankai.boot.resource.autoconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;

/**
 * @author fanxuankai
 */
public class ResourceServerAutoConfiguration {
    /**
     * 资源服务器
     *
     * @author fanxuankai
     */
    @Configuration
    @EnableResourceServer
    @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
        @Resource
        private TokenStore tokenStore;
        @Resource
        private Environment environment;

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
            String resourceId = environment.getProperty("spring.application.name");
            resources.resourceId(resourceId).tokenStore(tokenStore);
        }
    }

    @Configuration
    protected static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated();
        }
    }
}
