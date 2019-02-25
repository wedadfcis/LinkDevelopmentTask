package com.example.wedadabdelkareem.linkdevelopmenttask.service;

import android.Manifest;
import android.support.annotation.RequiresPermission;

import com.example.wedadabdelkareem.linkdevelopmenttask.service.model.Article;
import com.example.wedadabdelkareem.linkdevelopmenttask.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static volatile Retrofit sRetrofit = null;
    private static ArticleService articleService;

    public RetrofitClient() {
    }

    public static ArticleService getApiService() {
        return initRetrofitService();
    }

    private static ArticleService initRetrofitService() {
        if (articleService == null) {
            synchronized (RetrofitClient.class) {
                if (articleService == null) {
                    articleService = getRetrofit().create(ArticleService.class);
                }
            }
        }
        return articleService;
    }

    @RequiresPermission(Manifest.permission.INTERNET)
    private synchronized static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            synchronized (RetrofitClient.class) {
                if (sRetrofit == null) {
                    Gson gson = new GsonBuilder().setLenient().create();
                    sRetrofit = new Retrofit.Builder()
                            .baseUrl(ApiConstants.BASE_URL)
                            .client(createClient())
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
                }
            }
        }
        return sRetrofit;
    }

    private static OkHttpClient createClient() {
        return new OkHttpClient.Builder()
                .readTimeout(ApiConstants.READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(ApiConstants.READ_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }
}
