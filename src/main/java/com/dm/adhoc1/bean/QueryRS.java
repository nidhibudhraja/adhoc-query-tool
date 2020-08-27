package com.dm.adhoc1.bean;

import java.util.List;

public class QueryRS {
    List<String[]> resultSet;
    String queryStr ;

    public QueryRS(String query,List<String[]> result) {
        this.queryStr=query;
        this.resultSet = result;
    }

    public String getQueryStr() {
        return queryStr;
    }

    public void setQueryStr(String query) {
        this.queryStr = query;
    }

    public List<String[]> getResultSet() {
        return resultSet;
    }

    public void setResultSet(List<String[]> result) {
        this.resultSet = result;
    }
}
