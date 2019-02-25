package com.example.wedadabdelkareem.linkdevelopmenttask.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.wedadabdelkareem.linkdevelopmenttask.service.model.Article;

public class ArticleDetailsViewModel extends AndroidViewModel {
    private Article selectedArticle;

    public void setSelectedArticle(Article selectedArticle) {
        this.selectedArticle = selectedArticle;
    }

    public ArticleDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public Article getSelectedArticle() {
        return selectedArticle;
    }
}
