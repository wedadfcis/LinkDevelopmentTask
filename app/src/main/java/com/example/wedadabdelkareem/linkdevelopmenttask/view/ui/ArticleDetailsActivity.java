package com.example.wedadabdelkareem.linkdevelopmenttask.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.wedadabdelkareem.linkdevelopmenttask.R;
import com.example.wedadabdelkareem.linkdevelopmenttask.service.model.Article;
import com.example.wedadabdelkareem.linkdevelopmenttask.util.Constants;
import com.example.wedadabdelkareem.linkdevelopmenttask.viewmodel.ArticleDetailsViewModel;

public class ArticleDetailsActivity extends BaseActivity {
    private ArticleDetailsViewModel articleDetailsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.app_bar_main);
        setContentView(R.layout.activity_article_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setArticleData();
        displayArticleFragment();

    }

    private void setArticleData()
    {
        articleDetailsViewModel = ViewModelProviders.of(this).get(ArticleDetailsViewModel.class);
        if (getIntent().getExtras().getParcelable(Constants.ARTICLE) != null) {
            //set at view model
            articleDetailsViewModel.setSelectedArticle((Article) getIntent().getExtras().getParcelable(Constants.ARTICLE));
        }
    }

    private void displayArticleFragment()
    {
        ArticleDetailsFragment articleDetailsFragment = new ArticleDetailsFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,articleDetailsFragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
