package com.fanxuankai.boot.canal.mq.config.autoconfigure;

import com.fanxuankai.boot.canal.mq.config.AnnotationCanalListenerFactory;
import com.fanxuankai.canal.mq.core.listener.ConsumerHelper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 * @author fanxuankai
 */
@EnableConfigurationProperties(CanalMqProperties.class)
@Import({AnnotationCanalListenerFactory.class, ConsumerHelper.class})
public class CanalMqAutoConfiguration {

}
