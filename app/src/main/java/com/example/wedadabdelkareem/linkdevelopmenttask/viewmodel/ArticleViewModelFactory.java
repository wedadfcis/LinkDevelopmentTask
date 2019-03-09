package com.example.wedadabdelkareem.linkdevelopmenttask.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.wedadabdelkareem.linkdevelopmenttask.service.model.Article;

public class ArticleViewModelFactory  implements ViewModelProvider.Factory {
    Application application;
    Article selectedArticle;

    public ArticleViewModelFactory(Application application, Article selectedArticle) {
        this.application = application;
        this.selectedArticle = selectedArticle;
    }



    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ArticleDetailsViewModel(application, selectedArticle);
    }
}
