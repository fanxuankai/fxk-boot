package com.fanxuankai.boot.log.autoconfigure;

import cn.hutool.core.util.StrUtil;
import com.fanxuankai.boot.log.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import javax.sql.DataSource;

/**
 * @author fanxuankai
 */
@EnableConfigurationProperties({LogProperties.class})
public class LogAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public LogPointcutAdvisor logPointcutAdvisor(LogMethodInterceptor logMethodInterceptor) {
        return new LogPointcutAdvisor(logMethodInterceptor);
    }

    @Bean
    @ConditionalOnMissingBean
    public LogMethodInterceptor logMethodInterceptor(@Autowired(required = false) LogStore logStore,
                                                     @Autowired(required = false) UsernameService usernameService) {
        return new LogMethodInterceptor(logStore, usernameService);
    }

    @Configuration
    @Conditional(JdbcCondition.class)
    protected static class JdbcLogStoreConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public JdbcLogStore jdbcLogStore(DataSource dataSource) {
            return new JdbcLogStore(dataSource);
        }
    }

    @Configuration
    @Conditional(LoggerCondition.class)
    protected static class LoggerLogStoreConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public LoggerLogStore loggerLogStore() {
            return new LoggerLogStore();
        }
    }

    private static class JdbcCondition extends SpringBootCondition {
        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            ConditionMessage.Builder message = ConditionMessage.forCondition("Log Store Jdbc Condition");
            Environment environment = context.getEnvironment();
            String keyValue = environment.getProperty("log.log-store");
            if (StrUtil.equals(keyValue, com.fanxuankai.boot.log.enums.LogStore.JDBC.name(), true)) {
                return ConditionOutcome.match(message.foundExactly("provided private or symmetric key"));
            }
            return ConditionOutcome.noMatch(message.didNotFind("provided private or symmetric key").atAll());
        }
    }

    private static class LoggerCondition extends SpringBootCondition {
        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            ConditionMessage.Builder message = ConditionMessage.forCondition("Log Store Logger Condition");
            Environment environment = context.getEnvironment();
            String keyValue = environment.getProperty("log.log-store");
            if (StrUtil.equals(keyValue, com.fanxuankai.boot.log.enums.LogStore.LOGGER.name(), true)) {
                return ConditionOutcome.match(message.foundExactly("provided private or symmetric key"));
            }
            return ConditionOutcome.noMatch(message.didNotFind("provided private or symmetric key").atAll());
        }
    }
}