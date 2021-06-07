package com.fanxuankai.boot.resource.server.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author fanxuankai
 */
@Configuration
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
}
