package com.fanxuankai.boot.authorization.server.autoconfigure;

import com.fanxuankai.boot.authorization.server.Oauth2AccessDecisionManager;
import com.fanxuankai.boot.authorization.server.Oauth2AuthorizationServerAdapter;
import com.fanxuankai.boot.authorization.server.Oauth2ExceptionTranslator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * 认证服务器配置
 *
 * @author fanxuankai
 */
@Configuration
@EnableConfigurationProperties(AuthorizationServerProperties.class)
@EnableAuthorizationServer
@Import({AuthorizationServerTokenServicesConfiguration.class, WebSecurityAutoConfiguration.class})
public class AuthorizationServerAutoConfiguration {
    /**
     * 配置 token 的访问端点和 token 服务
     *
     * @return /
     */
    @Bean
    @ConditionalOnMissingBean
    public AuthorizationServerConfigurer authorizationServerConfigurer() {
        return new Oauth2AuthorizationServerAdapter();
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
}
