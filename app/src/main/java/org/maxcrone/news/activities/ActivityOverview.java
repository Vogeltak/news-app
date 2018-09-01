package org.maxcrone.news.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.maxcrone.news.adapters.NewsListAdapter;
import org.maxcrone.news.R;
import org.maxcrone.news.data.Article;
import org.maxcrone.news.network.NewsApi;
import org.maxcrone.news.util.CacheOperations;

import java.io.File;
import java.util.Arrays;

import okhttp3.Cache;

public class ActivityOverview extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String ARTICLE_OBJECT = "org.maxcrone.news.article";
    private RecyclerView mRecyclerView;
    private NewsListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NewsApi newsApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        /*
         * Install application wide http cache
         */
        try {
            File httpCacheDir = new File(getCacheDir(), "http");
            long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
            HttpResponseCache.install(httpCacheDir, httpCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*
         * Set up the Toolbar
         */
        Toolbar newsToolbar = findViewById(R.id.newsToolbar);
        setSupportActionBar(newsToolbar);

        // Disable automatic title, because we use a TextView as title for customization purposes.
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        /*
         * Set up the news RecyclerView
         */
        mRecyclerView = findViewById(R.id.newsRecyclerView);

        // Initialize a linear layoutmanager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Initialize with an empty adapter
        mAdapter = new NewsListAdapter(new Article[0]);
        mRecyclerView.setAdapter(mAdapter);

        // Initialize the Swipe Refresh Layout
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshContainer);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Initialize divider item decoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), 1);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Retrieve all articles from the api call
        newsApi = new NewsApi(this);
        Article[] data = newsApi.get();

        // Only if there are articles to show, we set an adapter
        // Otherwise we have to display a message to the user notifying it of the empty list of articles
        if (data.length >= 1) {
            // Sort the list of articles by date (most recent first)
            Arrays.sort(data);

            // Change dataset of the adapter
            mAdapter.swap(data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overview, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        // Handle item selection
        switch (menuItem.getItemId()) {
            case R.id.appSettings:
                return true;
            case R.id.refreshArticles:
                refreshArticleList();
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onRefresh() {
        refreshArticleList();
    }

    public void refreshArticleList() {
        mSwipeRefreshLayout.setRefreshing(true);

        CacheOperations.wipeCache(this);
        Article[] data = newsApi.get();
        Arrays.sort(data);
        mAdapter.swap(data);

        mSwipeRefreshLayout.setRefreshing(false);
    }
}
