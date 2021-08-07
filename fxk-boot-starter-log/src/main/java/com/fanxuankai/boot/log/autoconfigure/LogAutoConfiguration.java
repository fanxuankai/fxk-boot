package com.fanxuankai.boot.log.autoconfigure;

import cn.hutool.core.util.StrUtil;
import com.fanxuankai.boot.log.ClientInfoService;
import com.fanxuankai.boot.log.DefaultLogDetailServiceImpl;
import com.fanxuankai.boot.log.LogDetailService;
import com.fanxuankai.boot.log.RequestClientInfoServiceImpl;
import com.fanxuankai.boot.log.annotation.Log;
import com.fanxuankai.boot.log.enums.StoreType;
import com.fanxuankai.boot.log.interceptor.LogMethodInterceptor;
import com.fanxuankai.boot.log.store.JdbcLogStore;
import com.fanxuankai.boot.log.store.LogStore;
import com.fanxuankai.boot.log.store.LoggerLogStore;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import javax.sql.DataSource;

/**
 * 自动装配
 *
 * @author fanxuankai
 */
@EnableConfigurationProperties({LogProperties.class})
public class LogAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public LogDetailService logDetailService() {
        return new DefaultLogDetailServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnWebApplication
    public ClientInfoService clientInfoService() {
        return new RequestClientInfoServiceImpl();
    }

    @Configuration
    @Conditional(JdbcCondition.class)
    protected static class JdbcLogStoreConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public LogStore logStore(LogProperties logProperties, DataSource dataSource) {
            return new JdbcLogStore(logProperties, dataSource);
        }
    }

    @Configuration
    @Conditional(LoggerCondition.class)
    protected static class LoggerLogStoreConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public LogStore logStore() {
            return new LoggerLogStore();
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public LogStore logStore() {
        return new LoggerLogStore();
    }

    @Bean
    @ConditionalOnMissingBean
    public LogMethodInterceptor logMethodInterceptor(LogStore logStore, LogDetailService logDetailService,
                                                     @Autowired(required = false) ClientInfoService clientInfoService) {
        return new LogMethodInterceptor(logStore, logDetailService, clientInfoService);
    }

    @Bean
    public Advisor logAnnotationPointcutAdvisor(LogMethodInterceptor logMethodInterceptor) {
        return new DefaultPointcutAdvisor(AnnotationMatchingPointcut.forMethodAnnotation(Log.class),
                logMethodInterceptor);
    }

    @Bean
    @Conditional(PointcutExpressionsCondition.class)
    public Advisor logPointcutExpressionsCondition(LogMethodInterceptor logMethodInterceptor,
                                                   LogProperties logProperties) {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setAdvice(logMethodInterceptor);
        advisor.setExpression(logProperties.getExpressions());
        return advisor;
    }

    private static class JdbcCondition extends SpringBootCondition {
        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            ConditionMessage.Builder message = ConditionMessage.forCondition("Log Store Jdbc Condition");
            Environment environment = context.getEnvironment();
            String keyValue = environment.getProperty(LogProperties.STORE_TYPE);
            if (StrUtil.equals(keyValue, StoreType.JDBC.name(), true)) {
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
            String keyValue = environment.getProperty(LogProperties.STORE_TYPE);
            if (StrUtil.equals(keyValue, StoreType.LOGGER.name(), true)) {
                return ConditionOutcome.match(message.foundExactly("provided private or symmetric key"));
            }
            return ConditionOutcome.noMatch(message.didNotFind("provided private or symmetric key").atAll());
        }
    }

    private static class PointcutExpressionsCondition extends SpringBootCondition {
        @Override
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            ConditionMessage.Builder message = ConditionMessage.forCondition("Pointcut Expressions Condition");
            Environment environment = context.getEnvironment();
            if (StrUtil.isNotBlank(environment.getProperty(LogProperties.EXPRESSIONS))) {
                return ConditionOutcome.match(message.foundExactly("provided private or symmetric key"));
            }
            return ConditionOutcome.noMatch(message.didNotFind("provided private or symmetric key").atAll());
        }
    }
}