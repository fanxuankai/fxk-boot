package com.fanxuankai.boot.canal.redis.model;

import java.util.List;

/**
 * @author fanxuankai
 */
public class CombineKeyModel {
    private List<Entry> entries;

    public CombineKeyModel(List<Entry> entries) {
        this.entries = entries;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }
}
