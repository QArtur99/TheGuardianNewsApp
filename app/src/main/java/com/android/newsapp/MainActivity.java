package com.android.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsObject>> {


    private NewsAdapter newsAdapter;
    private TextView emptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadApp();
    }

    private void loadApp() {
        setContentView(R.layout.activity_main);
        emptyStateTextView = (TextView) findViewById(R.id.emptyView);
        newsAdapter = new NewsAdapter(this, new ArrayList<NewsObject>());
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setEmptyView(emptyStateTextView);
        listView.setAdapter(newsAdapter);

        if (checkConnection()) {
            getSupportLoaderManager().initLoader(1, null, this).forceLoad();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    NewsObject newsObject = newsAdapter.getItem(position);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsObject.webUrl));
                    startActivity(intent);
                }
            });
        } else {
            setContentView(R.layout.no_internet_connection);
            ButterKnife.bind(this);
        }
    }

    @OnClick(R.id.tryAgain)
    public void tryAgain() {
        loadApp();
    }

    private boolean checkConnection() {
        ConnectivityManager cm = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    @Override
    public Loader<List<NewsObject>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(MainActivity.this, "");
    }

    @Override
    public void onLoadFinished(Loader<List<NewsObject>> loader, List<NewsObject> data) {

        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        emptyStateTextView.setText(R.string.no_news);
        newsAdapter.clear();
        if (data != null && !data.isEmpty()) {
            newsAdapter.setBooks(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsObject>> loader) {
        newsAdapter.setBooks(new ArrayList<NewsObject>());
    }
}
