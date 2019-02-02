package com.example.wedadabdelkareem.linkdevelopmenttask.service.model;

import android.databinding.BindingAdapter;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wedadabdelkareem.linkdevelopmenttask.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Article implements Parcelable {
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;

    public Article() {
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(view);
    }

    @BindingAdapter({"bind:date"})
    public static void getDate(TextView textView, String timeStr) {
        textView.setText(timeStr.substring(0, timeStr.indexOf("T")));

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(publishedAt);
        dest.writeString(url);
        dest.writeString(urlToImage);

    }

    public Article(Parcel parcelable) {
        author = parcelable.readString();
        title = parcelable.readString();
        description = parcelable.readString();
        publishedAt = parcelable.readString();
        url = parcelable.readString();
        urlToImage = parcelable.readString();
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {

        @Override
        public Article createFromParcel(Parcel parcel) {
            return new Article(parcel);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[0];
        }
    };
}
