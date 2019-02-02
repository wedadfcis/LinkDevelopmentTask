package com.example.wedadabdelkareem.linkdevelopmenttask.view.ui;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wedadabdelkareem.linkdevelopmenttask.databinding.FragmentArticleListBinding;
import com.example.wedadabdelkareem.linkdevelopmenttask.R;
import com.example.wedadabdelkareem.linkdevelopmenttask.service.model.Article;
import com.example.wedadabdelkareem.linkdevelopmenttask.service.model.ArticleResponse;
import com.example.wedadabdelkareem.linkdevelopmenttask.util.ConnectionLiveData;
import com.example.wedadabdelkareem.linkdevelopmenttask.util.ConnectionModel;
import com.example.wedadabdelkareem.linkdevelopmenttask.util.Constants;
import com.example.wedadabdelkareem.linkdevelopmenttask.view.adapter.ArticleListAdapter;
import com.example.wedadabdelkareem.linkdevelopmenttask.view.callback.ArticleClickCallBack;
import com.example.wedadabdelkareem.linkdevelopmenttask.viewmodel.ArticleListViewModel;

import java.util.ArrayList;
import java.util.List;


public class ArticleListFragment extends Fragment {
    private ArticleListAdapter articleListAdapter;
    private FragmentArticleListBinding fragmentArticleListBinding;
    private ArticleClickCallBack articleClickCallBack;
    private ConnectionLiveData connectionLiveData;
    private ArticleListViewModel articleListViewModel;
    public ArticleListFragment() {
        // Required empty public constructor
    }

    // Store the listener (activity) that will have events fired once the fragment is attached
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ArticleClickCallBack) {
            articleClickCallBack = (ArticleClickCallBack) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement callback");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void observeViewModel() {
        // update list when get data
        articleListViewModel.getArticleListObservable().observe(this, new Observer<ArticleResponse>() {
            @Override
            public void onChanged(@Nullable ArticleResponse articleResponse) {
                fragmentArticleListBinding.loginProgress.setVisibility(View.GONE);
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
        articleListViewModel = ViewModelProviders.of(this).get(ArticleListViewModel.class);
        observeViewModel();
    }

    //observer on connection
    private void observeOnNetworkConnection() {
        connectionLiveData.observe(this, new Observer<ConnectionModel>() {
            @Override
            public void onChanged(@Nullable ConnectionModel connection) {
                if (!connection.getIsConnected()) {
                    Toast.makeText(getContext(), getActivity().getResources().getString(R.string.networkErrorCode), Toast.LENGTH_SHORT).show();
                    fragmentArticleListBinding.loginProgress.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentArticleListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_article_list, container, false);
        fragmentArticleListBinding.articleList.setLayoutManager(new LinearLayoutManager(getActivity()));
        articleListAdapter = new ArticleListAdapter(articleClickCallBack, getActivity());
        fragmentArticleListBinding.articleList.setAdapter(articleListAdapter);
        return (View) fragmentArticleListBinding.getRoot();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        articleClickCallBack = null;

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
                // filter recycler view when query submitted
                showFlightData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                showFlightData(query);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void showFlightData(String query){
        articleListAdapter.setFilter(articleListViewModel.getArticleFilterList(query).getValue());
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
