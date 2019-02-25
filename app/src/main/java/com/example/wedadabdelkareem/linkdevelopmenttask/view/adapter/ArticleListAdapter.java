package com.example.wedadabdelkareem.linkdevelopmenttask.view.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wedadabdelkareem.linkdevelopmenttask.R;
import com.example.wedadabdelkareem.linkdevelopmenttask.service.model.Article;
import com.example.wedadabdelkareem.linkdevelopmenttask.view.callback.ArticleClickCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder> {

    private List<Article> articleList;
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
        View v = LayoutInflater.from(context).inflate(R.layout.article_list_item, parent, false);
        return new ArticleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article currentArticle = articleList.get(position);
        ImageAndDateLoader.loadImage(holder.articleImage, currentArticle.getUrlToImage());
        holder.articleTitle.setText(currentArticle.getTitle());
        holder.authorName.setText(context.getString(R.string.by) + " " + currentArticle.getAuthor());
        ImageAndDateLoader.loadDate(holder.publishDate, currentArticle.getPublishedAt());
    }

    @Override
    public int getItemCount() {
        if (articleList != null)
            return articleList.size();
        else
            return 0;
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.article_image)
        ImageView articleImage;
        @BindView(R.id.article_title)
        TextView articleTitle;
        @BindView(R.id.by)
        TextView authorName;
        @BindView(R.id.publish_date)
        TextView publishDate;
        @BindView(R.id.article_card_view)
        CardView articleCardView;


        public ArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            articleCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (articleClickCallBack != null) {
                        articleClickCallBack.onClick(articleList.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
