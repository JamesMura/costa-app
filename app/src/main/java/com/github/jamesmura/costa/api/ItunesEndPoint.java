package com.github.jamesmura.costa.api;

import com.github.jamesmura.costa.models.ApiResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ItunesEndPoint {
    @GET("/search")
    void search(@Query("term") String term, Callback<ApiResponse> responseCallback);
}
