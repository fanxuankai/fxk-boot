package com.fanxuankai.boot.generator.strategy.model;

/**
 * @author fanxuankai
 */
public class QueryCriteriaTemplateData extends TemplateData {
    private boolean queryHasList;
    private boolean queryHasTimestamp;
    private boolean queryHasDate;
    private boolean queryHasBigDecimal;

    public boolean isQueryHasList() {
        return queryHasList;
    }

    public void setQueryHasList(boolean queryHasList) {
        this.queryHasList = queryHasList;
    }

    public boolean isQueryHasTimestamp() {
        return queryHasTimestamp;
    }

    public void setQueryHasTimestamp(boolean queryHasTimestamp) {
        this.queryHasTimestamp = queryHasTimestamp;
    }

    public boolean isQueryHasDate() {
        return queryHasDate;
    }

    public void setQueryHasDate(boolean queryHasDate) {
        this.queryHasDate = queryHasDate;
    }

    public boolean isQueryHasBigDecimal() {
        return queryHasBigDecimal;
    }

    public void setQueryHasBigDecimal(boolean queryHasBigDecimal) {
        this.queryHasBigDecimal = queryHasBigDecimal;
    }
}
