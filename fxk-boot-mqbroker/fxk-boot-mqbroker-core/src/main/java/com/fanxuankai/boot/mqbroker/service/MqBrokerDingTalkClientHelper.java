package com.fanxuankai.boot.mqbroker.service;

import com.fanxuankai.boot.mqbroker.autoconfigure.MqBrokerProperties;
import com.fanxuankai.commons.util.DingTalkClientHelper;
import org.springframework.stereotype.Service;

/**
 * @author fanxuankai
 */
@Service
public class MqBrokerDingTalkClientHelper {
    private final MqBrokerProperties mqBrokerProperties;
    private final DingTalkClientHelper dingTalkClientHelper;

    public MqBrokerDingTalkClientHelper(MqBrokerProperties mqBrokerProperties) {
        this.mqBrokerProperties = mqBrokerProperties;
        MqBrokerProperties.DingTalk dingTalk = mqBrokerProperties.getDingTalk();
        this.dingTalkClientHelper = DingTalkClientHelper.newInstance(dingTalk.getUrl(), dingTalk.getAccessToken(),
                dingTalk.getSecret());
    }

    public void push(String title, String group, String topic, String code) {
        if (!mqBrokerProperties.getDingTalk().isEnabled()) {
            return;
        }
        dingTalkClientHelper.sendRobotMarkdown(title, "#### " + title + "\n" +
                "> 分组: " + group + "\n\n" +
                "> 主题: " + topic + "\n\n" +
                "> 代码: " + code + "\n\n" +
                "> 服务器环境: " + mqBrokerProperties.getDingTalk().getEnv() + "\n\n");
    }

    public void push(String title, String group, String topic, String code, int retry, String ip) {
        if (!mqBrokerProperties.getDingTalk().isEnabled()) {
            return;
        }
        dingTalkClientHelper.sendRobotMarkdown(title, "#### " + title + "\n" +
                "> 分组: " + group + "\n\n" +
                "> 主题: " + topic + "\n\n" +
                "> 代码: " + code + "\n\n" +
                "> 重试次数: " + retry + "\n\n" +
                "> 服务器 IP: " + ip + "\n\n" +
                "> 服务器环境: " + mqBrokerProperties.getDingTalk().getEnv() + "\n\n");
    }
}
