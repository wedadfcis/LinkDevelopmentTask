package com.example.wedadabdelkareem.linkdevelopmenttask.view.adapter;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.wedadabdelkareem.linkdevelopmenttask.R;
import com.example.wedadabdelkareem.linkdevelopmenttask.databinding.ArticleListItemBinding;
import com.example.wedadabdelkareem.linkdevelopmenttask.service.model.Article;
import com.example.wedadabdelkareem.linkdevelopmenttask.view.callback.ArticleClickCallBack;

import java.util.ArrayList;
import java.util.List;


public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder> implements Filterable {

    private List< Article> articleList ;
    private final ArticleClickCallBack articleClickCallBack;
    private Context context;

    public ArticleListAdapter(ArticleClickCallBack articleClickCallBack, Context context) {
        this.articleClickCallBack = articleClickCallBack;
        this.context = context;
        this.articleList = new ArrayList<Article>();

    }
    public void setFilter(final List<Article> filteredArticleList) {
        this.articleList = filteredArticleList;
        notifyDataSetChanged();
    }
    public void setArticleList(final List<Article> articleList) {
            this.articleList = articleList;
            notifyItemRangeInserted(0, articleList.size());


    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ArticleListItemBinding articleListItemBinding = DataBindingUtil
                .inflate(inflater, R.layout.article_list_item,
                        parent, false);
        articleListItemBinding.setCallback(articleClickCallBack);

        articleListItemBinding.setCallback(new ArticleClickCallBack() {
            @Override
            public void onClick(Article article) {
                articleClickCallBack.onClick(article);
            }
        });
        return new ArticleViewHolder(articleListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        holder.binding.setArticle(articleList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (articleList != null)
        return articleList.size();
        else
            return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    articleList = articleList;
                } else {
                    List<Article> filteredList = new ArrayList<>();
                    for (Article row : articleList) {
                        // filter with author name and publish at
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())
                                || row.getAuthor().toLowerCase().contains(charString.toLowerCase())
                                || row.getPublishedAt().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    articleList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = articleList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                articleList = (ArrayList<Article>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {

        final ArticleListItemBinding binding;

        ArticleViewHolder(ArticleListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
