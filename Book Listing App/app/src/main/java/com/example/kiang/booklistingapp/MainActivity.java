package com.example.kiang.booklistingapp;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    private String mRequestUrl = "https://www.googleapis.com/books/v1/volumes?q=";
    private BookAdapter mAdapter;
    private String mQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.book_list);
        listView.setEmptyView(findViewById(R.id.empty_list_view));
        View loadingIndicator = findViewById(R.id.progress_bar);
        loadingIndicator.setVisibility(GONE);
        getLoaderManager().initLoader(1, null, MainActivity.this);

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menuSearch));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ListView listView = (ListView) findViewById(R.id.book_list);
                mQuery = newText;
                if (mQuery.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No result", Toast.LENGTH_SHORT).show();
                }
                mRequestUrl = "https://www.googleapis.com/books/v1/volumes?q=";
                mRequestUrl = mRequestUrl + mQuery;

                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {
                    Log.i("onQueryTextSubmit", "mRequestUrl value is: " + mRequestUrl);
                    getLoaderManager().restartLoader(1, null, MainActivity.this);
                    Log.i("onClick", "loader restarted");
                    View progressBar = findViewById(R.id.progress_bar);
                    progressBar.setVisibility(View.VISIBLE);
                    mAdapter = new BookAdapter(MainActivity.this, new ArrayList<Book>());
                    listView.setAdapter(mAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "No internet connection found", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        return true;
    }


    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        Log.i("onCreateLoader", "loader created");
        return new BookLoader(this, mRequestUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        Log.i("onLoadFinished", "loader finished");
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        Log.i("onLoaderReset", "loader reset");
    }

}
