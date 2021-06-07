package com.fanxuankai.boot.authorization.server.autoconfigure;

import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.util.Objects;

/**
 * @author fanxuankai
 */
@Configuration
public class AuthorizationServerTokenServicesConfiguration {
    @Configuration
    @Conditional(JwtTokenCondition.class)
    protected static class JwtTokenServiceConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public DefaultTokenServices jwtTokenServices(TokenStore tokenStore) {
            DefaultTokenServices services = new DefaultTokenServices();
            services.setTokenStore(tokenStore);
            return services;
        }

        @Bean
        @ConditionalOnMissingBean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            KeyPair keyPair =
                    new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray()).getKeyPair(
                            "mytest", "mypass".toCharArray());
            converter.setKeyPair(keyPair);
            return converter;
        }

        @Bean
        @ConditionalOnMissingBean
        public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
            return new JwtTokenStore(jwtAccessTokenConverter);
        }
    }

    @Configuration
    @Conditional(JdbcTokenCondition.class)
    protected static class JdbcTokenServiceConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public DefaultTokenServices jwtTokenServices(TokenStore tokenStore) {
            DefaultTokenServices services = new DefaultTokenServices();
            services.setTokenStore(tokenStore);
            return services;
        }

        @Bean
        @ConditionalOnMissingBean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            KeyPair keyPair =
                    new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray()).getKeyPair(
                            "mytest", "mypass".toCharArray());
            converter.setKeyPair(keyPair);
            return converter;
        }

        @Bean
        @ConditionalOnMissingBean
        public TokenStore tokenStore(DataSource dataSource) {
            return new JdbcTokenStore(dataSource);
        }
    }

    @Configuration
    @Conditional(RedisTokenCondition.class)
    @ConditionalOnClass(RedisConnectionFactory.class)
    protected static class RedisTokenServiceConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public DefaultTokenServices jwtTokenServices(TokenStore tokenStore) {
            DefaultTokenServices services = new DefaultTokenServices();
            services.setTokenStore(tokenStore);
            return services;
        }

        @Bean
        @ConditionalOnMissingBean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            KeyPair keyPair =
                    new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray()).getKeyPair(
                            "mytest", "mypass".toCharArray());
            converter.setKeyPair(keyPair);
            return converter;
        }

        @Bean
        @ConditionalOnMissingBean
        public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory) {
            return new RedisTokenStore(redisConnectionFactory);
        }
    }

    private static class JwtTokenCondition extends SpringBootCondition {
        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            ConditionMessage.Builder message = ConditionMessage.forCondition("OAuth JWT Condition");
            Environment environment = context.getEnvironment();
            String keyValue = environment.getProperty("fxk.security.oauth2.authorization.token-store");
            String tokenStore = "Jwt";
            if (Objects.equals(tokenStore, keyValue)) {
                return ConditionOutcome.match(message.foundExactly("provided private or symmetric key"));
            }
            return ConditionOutcome.noMatch(message.didNotFind("provided private or symmetric key").atAll());
        }
    }

    private static class JdbcTokenCondition extends SpringBootCondition {
        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            ConditionMessage.Builder message = ConditionMessage.forCondition("OAuth Jdbc Condition");
            Environment environment = context.getEnvironment();
            String keyValue = environment.getProperty("fxk.security.oauth2.authorization.token-store");
            String tokenStore = "Jdbc";
            if (Objects.equals(tokenStore, keyValue)) {
                return ConditionOutcome.match(message.foundExactly("provided private or symmetric key"));
            }
            return ConditionOutcome.noMatch(message.didNotFind("provided private or symmetric key").atAll());
        }
    }

    private static class RedisTokenCondition extends SpringBootCondition {
        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            ConditionMessage.Builder message = ConditionMessage.forCondition("OAuth Redis Condition");
            Environment environment = context.getEnvironment();
            String keyValue = environment.getProperty("fxk.security.oauth2.authorization.token-store");
            String tokenStore = "Redis";
            if (Objects.equals(tokenStore, keyValue)) {
                return ConditionOutcome.match(message.foundExactly("provided private or symmetric key"));
            }
            return ConditionOutcome.noMatch(message.didNotFind("provided private or symmetric key").atAll());
        }
    }
}