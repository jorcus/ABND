package com.example.kiang.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;


/**
 * Loads a list of books by using AsyncTask to perform the
 * network request to the given URL.
 */
public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private String mQuery;

    public NewsLoader(Context context, String query) {
        super(context);
        mQuery = query;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     *
     * @returns the list of books.
     */
    @Override
    public List<News> loadInBackground() {
        if (mQuery == null) {
            return null;
        }
        List<News> books = QueryUtils.fetchBookData(mQuery);
        return books;
    }
}
