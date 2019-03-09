package com.example.wedadabdelkareem.linkdevelopmenttask.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.wedadabdelkareem.linkdevelopmenttask.R;
import com.example.wedadabdelkareem.linkdevelopmenttask.service.model.Article;
import com.example.wedadabdelkareem.linkdevelopmenttask.util.Constants;
import com.example.wedadabdelkareem.linkdevelopmenttask.view.base.BaseActivity;
import com.example.wedadabdelkareem.linkdevelopmenttask.viewmodel.ArticleDetailsViewModel;
import com.example.wedadabdelkareem.linkdevelopmenttask.viewmodel.ArticleViewModelFactory;

public class ArticleDetailsActivity extends BaseActivity {
    private ArticleDetailsViewModel articleDetailsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        setArticleData();
        displayArticleFragment();

    }

    private void setArticleData()
    {
        if (getIntent().getExtras().getParcelable(Constants.ARTICLE) != null) {
            //define ArticleViewFactory
            ArticleViewModelFactory factory = new ArticleViewModelFactory(getApplication(),(Article) getIntent().getExtras().getParcelable(Constants.ARTICLE));
            //define viewModel
            articleDetailsViewModel = ViewModelProviders.of(this,factory).get(ArticleDetailsViewModel.class);
        }
    }

    private void displayArticleFragment()
    {
        ArticleDetailsFragment articleDetailsFragment = new ArticleDetailsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,articleDetailsFragment).commit();
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
