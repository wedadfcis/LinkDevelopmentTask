package com.example.wedadabdelkareem.linkdevelopmenttask.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.wedadabdelkareem.linkdevelopmenttask.service.model.Article;

public class ArticleDetailsViewModel extends AndroidViewModel {


    private MutableLiveData<Boolean> isClickOpenWebsite = new MutableLiveData<>();
    private Article selectedArticle = new Article();

    public void setSelectedArticle(Article selectedArticle) {
        this.selectedArticle = selectedArticle;
    }

    public ArticleDetailsViewModel(@NonNull Application application) {
        super(application);
        this.isClickOpenWebsite.setValue(false);
    }

    public MutableLiveData<Boolean> getIsClickOpenWebsite() {
        return isClickOpenWebsite;
    }

    public void openWebsiteBtnClick(Article article) {
        isClickOpenWebsite.setValue(true);
    }

    public Article getSelectedArticle() {
        return selectedArticle;
    }
}
