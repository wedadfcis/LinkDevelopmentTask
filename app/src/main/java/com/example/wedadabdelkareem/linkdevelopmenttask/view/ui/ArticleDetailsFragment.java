package com.example.wedadabdelkareem.linkdevelopmenttask.view.ui;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wedadabdelkareem.linkdevelopmenttask.databinding.FragmentArticleDetailsBinding;
import com.example.wedadabdelkareem.linkdevelopmenttask.R;
import com.example.wedadabdelkareem.linkdevelopmenttask.service.model.Article;
import com.example.wedadabdelkareem.linkdevelopmenttask.util.Constants;
import com.example.wedadabdelkareem.linkdevelopmenttask.viewmodel.ArticleDetailsViewModel;


public class ArticleDetailsFragment extends Fragment {
    private FragmentArticleDetailsBinding fragmentArticleDetailsBinding;
    private ArticleDetailsViewModel articleDetailsViewModel;

    public ArticleDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        articleDetailsViewModel = ViewModelProviders.of(this).get(ArticleDetailsViewModel.class);
        observeViewModel(articleDetailsViewModel);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (getArguments().getParcelable(Constants.ARTICLE) != null) {
            //set at view model
            articleDetailsViewModel.setSelectedArticle((Article) getArguments().getParcelable(Constants.ARTICLE));
        }
        fragmentArticleDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_article_details, container, false);
        fragmentArticleDetailsBinding.setArticle(articleDetailsViewModel.getSelectedArticle());
        fragmentArticleDetailsBinding.setOpenwebsite(articleDetailsViewModel);
        return (View) fragmentArticleDetailsBinding.getRoot();
    }

    public void observeViewModel(final ArticleDetailsViewModel detailsViewModel) {
        detailsViewModel.getIsClickOpenWebsite().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean == true) {
                    try {
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(detailsViewModel.getSelectedArticle().getUrl()));
                        startActivity(myIntent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getActivity(), "fail_to_open_url", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
