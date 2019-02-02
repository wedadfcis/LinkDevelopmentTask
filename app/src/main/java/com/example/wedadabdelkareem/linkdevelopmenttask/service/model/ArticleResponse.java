package com.example.wedadabdelkareem.linkdevelopmenttask.service.model;

import com.example.wedadabdelkareem.linkdevelopmenttask.service.model.Article;

import java.util.List;

public class ArticleResponse {
    private List<Article>articles;
    private String  message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
