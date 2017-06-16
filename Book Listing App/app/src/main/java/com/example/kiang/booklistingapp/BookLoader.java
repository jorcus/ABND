package com.example.kiang.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of books by using AsyncTask to perform the
 * network request to the given URL.
 */
public class BookLoader extends AsyncTaskLoader<List<Book>> {
    private String mQuery;

    public BookLoader(Context context, String query) {
        super(context);
        mQuery = query;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     * @returns the list of books.
     */
    @Override
    public List<Book> loadInBackground() {
        if (mQuery == null) {
            return null;
        }
        List<Book> books = QueryUtils.fetchBookData(mQuery);
        return books;
    }
}
