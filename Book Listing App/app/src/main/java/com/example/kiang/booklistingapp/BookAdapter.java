package com.example.kiang.booklistingapp;


import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class BookAdapter extends ArrayAdapter<Book> {
    private static final String LOG_TAG = BookAdapter.class.getSimpleName();

    public BookAdapter(Activity context, ArrayList<Book> Book) {
        super(context, 0, Book);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_template, parent, false);
        }
        Book currentBook = getItem(position);


        Bitmap thumbnail = currentBook.getBitmap();
        ImageView bookThumbnail = (ImageView) listItemView.findViewById(R.id.book_thumbnail);
        bookThumbnail.setImageBitmap(thumbnail);

        TextView bookTitle = (TextView) listItemView.findViewById(R.id.book_title);
        bookTitle.setText(currentBook.getTitle());
        TextView bookAuthor = (TextView) listItemView.findViewById(R.id.book_author);
        bookAuthor.setText(currentBook.getAuthor());

        return listItemView;
    }
}