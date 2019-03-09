package com.example.wedadabdelkareem.linkdevelopmenttask.view.ui;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.wedadabdelkareem.linkdevelopmenttask.R;
import com.example.wedadabdelkareem.linkdevelopmenttask.service.model.Article;
import com.example.wedadabdelkareem.linkdevelopmenttask.service.model.ArticleResponse;
import com.example.wedadabdelkareem.linkdevelopmenttask.util.ConnectionLiveData;
import com.example.wedadabdelkareem.linkdevelopmenttask.util.ConnectionModel;
import com.example.wedadabdelkareem.linkdevelopmenttask.util.Constants;
import com.example.wedadabdelkareem.linkdevelopmenttask.view.adapter.ArticleListAdapter;
import com.example.wedadabdelkareem.linkdevelopmenttask.view.base.BaseFragment;
import com.example.wedadabdelkareem.linkdevelopmenttask.view.callback.ArticleClickCallBack;
import com.example.wedadabdelkareem.linkdevelopmenttask.viewmodel.ArticleListViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ArticleListFragment extends BaseFragment implements ArticleClickCallBack {
    private ArticleListAdapter articleListAdapter;
    private ConnectionLiveData connectionLiveData;
    private ArticleListViewModel articleListViewModel;
    private Unbinder unbinder;
    @BindView(R.id.loginProgress)
    ProgressBar loginProgress;
    @BindView(R.id.article_list)
    RecyclerView articleRecyclerView;

    public ArticleListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void bindData() {
        // update list when get data
        articleListViewModel.getArticleListObservable(getActivity()).observe(this, new Observer<ArticleResponse>() {
            @Override
            public void onChanged(@Nullable ArticleResponse articleResponse) {
                loginProgress.setVisibility(View.GONE);
                if (articleResponse.getMessage().equals(Constants.SUCCESS) && articleResponse.getArticles() != null) {
                    articleListAdapter.setArticleList(articleResponse.getArticles());
                } else if (articleResponse.getArticles() == null) {
                    Toast.makeText(getActivity(), articleResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        connectionLiveData = new ConnectionLiveData(getActivity());
        observeOnNetworkConnection();
        //------------------------------ inti view model -------------------------------//
        articleListViewModel = ViewModelProviders.of(this).get(ArticleListViewModel.class);
        //---------------------- setup recycler view -----------------------------------//
        initializeRecyclerView();
        //------------------------- bind article list data -----------------------------//
        bindData();

    }

    public void initializeRecyclerView() {
        articleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        articleListAdapter = new ArticleListAdapter(this, getActivity());
        articleRecyclerView.setAdapter(articleListAdapter);
    }

    //observer on connection
    private void observeOnNetworkConnection() {
        connectionLiveData.observe(this, new Observer<ConnectionModel>() {
            @Override
            public void onChanged(@Nullable ConnectionModel connection) {
                if (!connection.getIsConnected()) {
                    Toast.makeText(getContext(), getActivity().getResources().getString(R.string.networkErrorCode), Toast.LENGTH_SHORT).show();
                    loginProgress.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(Article article) {
        // navigate to article details
        Intent intent = new Intent(getActivity(), ArticleDetailsActivity.class);
        intent.putExtra(Constants.ARTICLE, article);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        if (null == getActivity())
            return;
        SearchView searchView;
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter  when query submitted
                filterData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter when text is changed
                filterData(query);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void filterData(String query) {
        articleListViewModel.getArticleFilterList(query).observe(getActivity(), new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                articleListAdapter.setFilter(articles);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
