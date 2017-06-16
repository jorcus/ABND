package com.example.kiang.newsapp;

import android.app.Activity;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class NewsAdapter extends ArrayAdapter<News> {
    private static final String LOG_TAG = NewsAdapter.class.getSimpleName();

    public NewsAdapter(Activity context, ArrayList<News> Book) {
        super(context, 0, Book);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_template, parent, false);
        }
        News currentNews = getItem(position);

        TextView newsTitle = (TextView) listItemView.findViewById(R.id.book_title);
        newsTitle.setText(currentNews.getTitle());



        ImageView newsBitmap = (ImageView) listItemView.findViewById(R.id.news_bitmap);
        newsBitmap.setImageBitmap(currentNews.getBitmap());
        return listItemView;
    }
}