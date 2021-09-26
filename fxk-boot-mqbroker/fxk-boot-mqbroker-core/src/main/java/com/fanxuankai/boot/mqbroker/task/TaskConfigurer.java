package com.fanxuankai.boot.mqbroker.task;

import com.fanxuankai.boot.mqbroker.autoconfigure.MqBrokerProperties;
import com.fanxuankai.boot.mqbroker.consume.EventListenerRegistry;
import com.fanxuankai.boot.mqbroker.service.MsgReceiveService;
import com.fanxuankai.boot.mqbroker.service.MsgSendService;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务配置
 *
 * @author fanxuankai
 */
@Component
public class TaskConfigurer implements SchedulingConfigurer {
    @Resource
    private MqBrokerProperties mqBrokerProperties;
    @Resource
    private MsgSendService msgSendService;
    @Resource
    private MsgReceiveService msgReceiveService;
    @Resource
    private MsgSendTask msgSendTask;
    @Resource
    private MsgReceiveTask msgReceiveTask;
    @Resource
    private EventListenerRegistry eventListenerRegistry;

    @Override
    public void configureTasks(@NonNull ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(msgSendTask, triggerContext ->
                new PeriodicTrigger(mqBrokerProperties.getDelayedSend().isEnabled() ?
                        mqBrokerProperties.getDelayedSend().getIntervalMillis() :
                        mqBrokerProperties.getIntervalMillis(), TimeUnit.MILLISECONDS)
                        .nextExecutionTime(triggerContext));
        scheduledTaskRegistrar.addTriggerTask(() -> msgSendService.publisherCallbackTimeout(),
                triggerContext -> new PeriodicTrigger(mqBrokerProperties.getPublisherCallbackTimeout(),
                        TimeUnit.MILLISECONDS).nextExecutionTime(triggerContext));
        if (!eventListenerRegistry.getAllListenerMetadata().isEmpty()) {
            scheduledTaskRegistrar.addTriggerTask(msgReceiveTask, triggerContext ->
                    new PeriodicTrigger(mqBrokerProperties.getIntervalMillis(), TimeUnit.MILLISECONDS)
                            .nextExecutionTime(triggerContext));
            scheduledTaskRegistrar.addTriggerTask(() -> msgReceiveService.consumeTimeout(),
                    triggerContext -> new PeriodicTrigger(mqBrokerProperties.getConsumeTimeout(),
                            TimeUnit.MILLISECONDS).nextExecutionTime(triggerContext));
        }
    }

}
