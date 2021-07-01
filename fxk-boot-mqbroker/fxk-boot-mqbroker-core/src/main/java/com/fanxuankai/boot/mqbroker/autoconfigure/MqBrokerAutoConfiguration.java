package com.fanxuankai.boot.mqbroker.autoconfigure;

import com.fanxuankai.boot.mqbroker.consume.AbstractMqConsumer;
import com.fanxuankai.boot.mqbroker.mapper.MsgSendMapper;
import com.fanxuankai.boot.mqbroker.produce.MqProducer;
import com.fanxuankai.boot.mqbroker.service.MsgSendService;
import com.fanxuankai.boot.mqbroker.task.TaskConfigurer;
import com.fanxuankai.commons.util.concurrent.ThreadPool;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author fanxuankai
 */
@EnableConfigurationProperties(MqBrokerProperties.class)
@MapperScan(basePackageClasses = MsgSendMapper.class)
@ComponentScan(basePackageClasses = {AbstractMqConsumer.class, MqProducer.class, MsgSendService.class,
        TaskConfigurer.class})
@EnableTransactionManagement
@EnableScheduling
public class MqBrokerAutoConfiguration {
    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    public ScheduledExecutorService scheduledExecutorService() {
        int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
        return new ScheduledThreadPoolExecutor(corePoolSize, new ThreadFactory() {
            private final AtomicLong count = new AtomicLong(0L);

            @Override
            public Thread newThread(@NonNull Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("mqbroker-scheduler-" + count.getAndIncrement());
                return thread;
            }
        });
    }

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    public ThreadPoolExecutor threadPoolExecutor() {
        return ThreadPool.INSTANCE.getExecutor();
    }
}
