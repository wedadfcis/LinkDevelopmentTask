package com.example.wedadabdelkareem.linkdevelopmenttask.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.wedadabdelkareem.linkdevelopmenttask.service.model.Article;
import com.example.wedadabdelkareem.linkdevelopmenttask.service.model.ArticleResponse;
import com.example.wedadabdelkareem.linkdevelopmenttask.service.repository.ArticleRepository;

import java.util.ArrayList;
import java.util.List;

public class ArticleListViewModel extends AndroidViewModel {
    private  LiveData<ArticleResponse> articleListObservable;
    private MutableLiveData<List<Article>> filterArticleList;
    public ArticleListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ArticleResponse> getArticleListObservable(Context context) {
        ArticleRepository articleRepository = new ArticleRepository();
         articleListObservable = articleRepository.getArticleList(context);
        return articleListObservable;
    }

    public LiveData<List<Article>> getArticleFilterList(String query) {
        filterArticleList = new MutableLiveData<>();
        List<Article> filteredList = new ArrayList<>();
        filteredList.clear();
      if(articleListObservable.getValue().getArticles().size()>0) {
          if (!query.isEmpty() || !query.equals("")) {
              for (Article currentArticle : articleListObservable.getValue().getArticles()) {
                  // filter with author name
                  if (currentArticle.getAuthor().toLowerCase().contains(query)) {
                      filteredList.add(currentArticle);
                  }
              }
          } else {
              filteredList = articleListObservable.getValue().getArticles();
          }
      }
     filterArticleList.setValue(filteredList);
     return filterArticleList;
}
}
