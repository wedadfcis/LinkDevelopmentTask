package com.example.wedadabdelkareem.linkdevelopmenttask.view.adapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wedadabdelkareem.linkdevelopmenttask.R;
import com.squareup.picasso.Picasso;

public class ImageAndDateLoader {
  public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(view);
    }
    public static void loadDate(TextView textView, String timeStr) {
        textView.setText(timeStr.substring(0, timeStr.indexOf("T")));

    }
}
