package com.example.wedadabdelkareem.linkdevelopmenttask.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.wedadabdelkareem.linkdevelopmenttask.service.model.Article;

public class ArticleDetailsViewModel extends AndroidViewModel {
    private MutableLiveData<Article> selectedArticle = new MutableLiveData<>();

    public ArticleDetailsViewModel(@NonNull Application application , Article selectedArticle) {
        super(application);
        this.selectedArticle.setValue(selectedArticle);
    }
    public MutableLiveData<Article> getArticleData()
    {
        return selectedArticle;
    }

}
