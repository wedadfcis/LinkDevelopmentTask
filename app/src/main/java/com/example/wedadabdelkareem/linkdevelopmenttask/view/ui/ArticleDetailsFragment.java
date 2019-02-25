package com.example.wedadabdelkareem.linkdevelopmenttask.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wedadabdelkareem.linkdevelopmenttask.R;
import com.example.wedadabdelkareem.linkdevelopmenttask.view.adapter.ImageAndDateLoader;
import com.example.wedadabdelkareem.linkdevelopmenttask.viewmodel.ArticleDetailsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ArticleDetailsFragment extends BaseFragment {
     private ArticleDetailsViewModel articleDetailsViewModel;
    @BindView(R.id.article_image)
    ImageView articleImage;
    @BindView(R.id.article_title)
    TextView articleTitle;
    @BindView(R.id.author_name)
    TextView authorName;
    @BindView(R.id.description)
    TextView articleDescription;
    @BindView(R.id.publish_date)
    TextView publishAt;
    @BindView(R.id.open_website)
    Button openWebsiteBtn;
    private Unbinder unbinder;

    public ArticleDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        articleDetailsViewModel = ViewModelProviders.of(getActivity()).get(ArticleDetailsViewModel.class);
        bindData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_article_details, container,false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void bindData() {
        //set article data
        ImageAndDateLoader.loadImage(articleImage,articleDetailsViewModel.getSelectedArticle().getUrlToImage());
        articleTitle.setText(articleDetailsViewModel.getSelectedArticle().getTitle());
        articleDescription.setText(articleDetailsViewModel.getSelectedArticle().getDescription());
        ImageAndDateLoader.loadDate(publishAt,articleDetailsViewModel.getSelectedArticle().getPublishedAt());
    }
    @OnClick(R.id.open_website)
    void openWebsite() {
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleDetailsViewModel.getSelectedArticle().getUrl()));
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.fail_open_url), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void filterData(String query) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
