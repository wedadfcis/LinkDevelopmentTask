package com.example.wedadabdelkareem.linkdevelopmenttask.service;

import com.example.wedadabdelkareem.linkdevelopmenttask.service.model.ArticleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArticleService {
    @GET("v1/articles?")
    Call<ArticleResponse> getProjectList(@Query("source") String source , @Query("apiKey") String apiKey);
}
