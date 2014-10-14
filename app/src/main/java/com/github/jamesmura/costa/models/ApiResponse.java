package com.github.jamesmura.costa.models;


import java.util.ArrayList;
import java.util.List;

public class ApiResponse {

    private Integer resultCount;
    private List<Result> results = new ArrayList<Result>();

    public Integer getResultCount() {
        return resultCount;
    }

    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

}
