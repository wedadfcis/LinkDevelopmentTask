package com.example.wedadabdelkareem.linkdevelopmenttask.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.wedadabdelkareem.linkdevelopmenttask.R;
import com.example.wedadabdelkareem.linkdevelopmenttask.service.model.ArticleResponse;
import com.example.wedadabdelkareem.linkdevelopmenttask.util.ConnectionLiveData;
import com.example.wedadabdelkareem.linkdevelopmenttask.util.ConnectionModel;
import com.example.wedadabdelkareem.linkdevelopmenttask.util.Constants;
import com.example.wedadabdelkareem.linkdevelopmenttask.view.adapter.ArticleListAdapter;
import com.example.wedadabdelkareem.linkdevelopmenttask.view.callback.ArticleClickCallBack;
import com.example.wedadabdelkareem.linkdevelopmenttask.viewmodel.ArticleListViewModel;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ArticleListFragment extends BaseFragment {
    private ArticleListAdapter articleListAdapter;
    private ArticleClickCallBack articleClickCallBack;
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
        //setHasOptionsMenu(true);
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
        articleListViewModel = ViewModelProviders.of(this).get(ArticleListViewModel.class);
        bindData();
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
        View view = inflater.inflate(R.layout.fragment_article_list,container,false);
        unbinder = ButterKnife.bind(this,view);
        articleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        articleListAdapter = new ArticleListAdapter(articleClickCallBack, getActivity());
        articleRecyclerView.setAdapter(articleListAdapter);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        articleClickCallBack = null;

    }

    @Override
    public void filterData(String query) {
        articleListAdapter.setFilter(articleListViewModel.getArticleFilterList(query).getValue());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
