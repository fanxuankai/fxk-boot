package com.fanxuankai.boot.mqbroker.config;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fanxuankai.boot.mqbroker.consume.AbstractMqConsumer;
import com.fanxuankai.boot.mqbroker.domain.Msg;
import com.fanxuankai.boot.mqbroker.domain.MsgReceive;
import com.fanxuankai.boot.mqbroker.domain.MsgSend;
import com.fanxuankai.boot.mqbroker.mapper.MsgSendMapper;
import com.fanxuankai.boot.mqbroker.produce.MqProducer;
import com.fanxuankai.boot.mqbroker.service.MsgSendService;
import com.fanxuankai.boot.mqbroker.task.TaskConfigurer;
import com.fanxuankai.commons.core.util.concurrent.ThreadPool;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.lang.reflect.Field;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author fanxuankai
 */
@Configuration
@EnableConfigurationProperties(MqBrokerProperties.class)
@MapperScan(basePackageClasses = MsgSendMapper.class)
@ComponentScan(basePackageClasses = {AbstractMqConsumer.class, MqProducer.class, MsgSendService.class,
        TaskConfigurer.class})
@EnableTransactionManagement
@EnableScheduling
public class MqBrokerAutoConfiguration implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (bean instanceof MqBrokerProperties) {
            afterMqBrokerPropertiesInitialization((MqBrokerProperties) bean);
        }
        return bean;
    }

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

    /**
     * MqBrokerProperties 初始化后, 判断是否指定了元数据, 如果指定了元数据, 则修改相应的注解属性值
     *
     * @param properties /
     */
    private void afterMqBrokerPropertiesInitialization(MqBrokerProperties properties) {
        MqBrokerProperties.MetaData metaData = properties.getMetaData();
        if (metaData != null) {
            if (StrUtil.isNotBlank(metaData.getMsgSendTable())) {
                AnnotationUtil.setValue(MsgSend.class.getAnnotation(TableName.class), "value",
                        metaData.getMsgSendTable());
            }
            if (StrUtil.isNotBlank(metaData.getMsgReceiveTable())) {
                AnnotationUtil.setValue(MsgReceive.class.getAnnotation(TableName.class), "value",
                        metaData.getMsgReceiveTable());
            }
            if (StrUtil.isNotBlank(metaData.getCreateDateColumn())) {
                Field createDate = ReflectUtil.getField(Msg.class, "createDate");
                TableField annotation = createDate.getAnnotation(TableField.class);
                AnnotationUtil.setValue(annotation, "value", metaData.getCreateDateColumn());
            }
            if (StrUtil.isNotBlank(metaData.getLastModifiedDateColumn())) {
                Field createDate = ReflectUtil.getField(Msg.class, "lastModifiedDate");
                TableField annotation = createDate.getAnnotation(TableField.class);
                AnnotationUtil.setValue(annotation, "value", metaData.getLastModifiedDateColumn());
            }
        }
    }
}
