package com.example.wedadabdelkareem.linkdevelopmenttask.service.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.wedadabdelkareem.linkdevelopmenttask.R;
import com.example.wedadabdelkareem.linkdevelopmenttask.service.ApiConstants;
import com.example.wedadabdelkareem.linkdevelopmenttask.service.ArticleService;
import com.example.wedadabdelkareem.linkdevelopmenttask.service.RetrofitClient;
import com.example.wedadabdelkareem.linkdevelopmenttask.service.model.ArticleResponse;
import com.example.wedadabdelkareem.linkdevelopmenttask.util.Constants;
import com.google.gson.stream.MalformedJsonException;


public class ArticleRepository {

    public ArticleRepository() {

    }

    public LiveData<ArticleResponse> getArticleList(final Context context) {
        final MutableLiveData<ArticleResponse> responseData = new MutableLiveData<>();
        RetrofitClient.getApiService().getProjectList(ApiConstants.SOURCE, ApiConstants.API_KEY).enqueue(new Callback<ArticleResponse>() {
            ArticleResponse articleResponse = new ArticleResponse();
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                articleResponse.setArticles(response.body().getArticles());
                articleResponse.setMessage(Constants.SUCCESS);
                responseData.setValue(articleResponse);
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                articleResponse.setArticles(null);
                articleResponse.setMessage(getCustomErrorMessage(t,context));
                responseData.setValue(articleResponse);
            }
        });
        return responseData;
    }

    private String getCustomErrorMessage(Throwable error,Context context) {

        if (error instanceof SocketTimeoutException) {
            return context.getString(R.string.requestTimeOutError);
        } else if (error instanceof MalformedJsonException) {
            return context.getResources().getString(R.string.responseMalformedJson);
        } else if (error instanceof IOException) {
            return context.getResources().getString(R.string.networkError);
        } else if (error instanceof HttpException) {
            return (((HttpException) error).response().message());
        } else {
            return context.getString(R.string.unknownError);
        }

    }


}

