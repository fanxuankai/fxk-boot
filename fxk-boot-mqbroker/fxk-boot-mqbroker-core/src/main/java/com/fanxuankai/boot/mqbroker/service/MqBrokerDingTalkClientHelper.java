package com.fanxuankai.boot.mqbroker.service;

import cn.hutool.core.codec.Base64;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.fanxuankai.boot.mqbroker.config.MqBrokerProperties;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * @author fanxuankai
 */
@Service
public class MqBrokerDingTalkClientHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MqBrokerDingTalkClientHelper.class);

    @Resource
    private MqBrokerProperties mqBrokerProperties;

    public void push(String title, String group, String topic, String code) {
        if (!Objects.equals(mqBrokerProperties.getDingTalk().getEnabled(), Boolean.TRUE)) {
            return;
        }
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("markdown");
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle(title);
        markdown.setText("#### " + title + "\n" +
                "> 分组: " + group + "\n\n" +
                "> 主题: " + topic + "\n\n" +
                "> 代码: " + code + "\n\n" +
                "> 服务器环境: " + mqBrokerProperties.getDingTalk().getEnv() + "\n\n"
        );
        request.setMarkdown(markdown);
        try {
            newDingTalkClient().execute(request);
        } catch (ApiException e) {
            LOGGER.error("钉钉推送异常", e);
        }
    }

    public void push(String title, String group, String topic, String code, int retry, String ip) {
        if (!Objects.equals(mqBrokerProperties.getDingTalk().getEnabled(), Boolean.TRUE)) {
            return;
        }
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype("markdown");
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle(title);
        markdown.setText("#### " + title + "\n" +
                "> 分组: " + group + "\n\n" +
                "> 主题: " + topic + "\n\n" +
                "> 代码: " + code + "\n\n" +
                "> 重试次数: " + retry + "\n\n" +
                "> 服务器 IP: " + ip + "\n\n" +
                "> 服务器环境: " + mqBrokerProperties.getDingTalk().getEnv() + "\n\n"
        );
        request.setMarkdown(markdown);
        try {
            newDingTalkClient().execute(request);
        } catch (ApiException e) {
            LOGGER.error("钉钉推送异常", e);
        }
    }

    public DingTalkClient newDingTalkClient() {
        MqBrokerProperties.DingTalk dingTalk = mqBrokerProperties.getDingTalk();
        String serviceUrl = dingTalk.getUrl();
        serviceUrl += "?access_token=" + dingTalk.getAccessToken();
        if (StringUtils.hasText(dingTalk.getSecret())) {
            try {
                Long timestamp = System.currentTimeMillis();
                String secret = dingTalk.getSecret();
                String stringToSign = timestamp + "\n" + secret;
                Mac mac = Mac.getInstance("HmacSHA256");
                mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
                byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
                String sign = URLEncoder.encode(Base64.encode(signData), "UTF-8");
                serviceUrl += "&timestamp=" + timestamp;
                serviceUrl += "&sign=" + sign;
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
                LOGGER.error("钉钉客户端初始化异常", e);
            }
        }
        return new DefaultDingTalkClient(serviceUrl);
    }

}
