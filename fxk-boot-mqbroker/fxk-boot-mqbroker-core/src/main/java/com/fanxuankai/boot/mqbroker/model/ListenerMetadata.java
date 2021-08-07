package com.fanxuankai.boot.mqbroker.model;

import java.util.Objects;

/**
 * 监听者信息
 *
 * @author fanxuankai
 */
public class ListenerMetadata {
    private String group;
    private String topic;
    private String name;
    private Integer waitRateSeconds;
    private Integer waitMaxSeconds;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWaitRateSeconds() {
        return waitRateSeconds;
    }

    public void setWaitRateSeconds(Integer waitRateSeconds) {
        this.waitRateSeconds = waitRateSeconds;
    }

    public Integer getWaitMaxSeconds() {
        return waitMaxSeconds;
    }

    public void setWaitMaxSeconds(Integer waitMaxSeconds) {
        this.waitMaxSeconds = waitMaxSeconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ListenerMetadata that = (ListenerMetadata) o;
        return Objects.equals(group, that.group) &&
                topic.equals(that.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, topic);
    }
}