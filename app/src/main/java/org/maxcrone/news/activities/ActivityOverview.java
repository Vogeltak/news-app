package org.maxcrone.news.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.maxcrone.news.adapters.NewsListAdapter;
import org.maxcrone.news.R;
import org.maxcrone.news.data.Article;
import org.maxcrone.news.network.NewsApi;

public class ActivityOverview extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "org.maxcrone.news.article";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

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

        // Retrieve all articles from the api call
        Article[] data;

        // Asynchronously retrieve the article list on another thread
        try {
            data = new ApiTask()
                    .execute()
                    .get();
        } catch (Exception e) {
            System.out.println(e.toString());

            // Set data to a standard empty value if something went wrong
            data = new Article[0];
        }

        // Only if there are articles to show, we set an adapter
        // Otherwise we have to display a message to the user notifying it of the empty list of articles
        if (data.length >= 1) {
            // Initialize an adapter
            mAdapter = new NewsListAdapter(data);
            mRecyclerView.setAdapter(mAdapter);
        }

        // Initialize divider item decoration
        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), 1);
        //mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    /* Called when the user taps an article */
    public void openArticle(View view) {
        Intent articleIntent = new Intent(this, ActivityArticle.class);

        // Only use window transitions if Android version is sufficiently high
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            /*View titleView = findViewById(R.id.textView5);
            View sourceView = findViewById(R.id.textView4);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
                    Pair.create(titleView, "articleTitle"),
                    Pair.create(sourceView, "articleSource"));*/
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
            startActivity(articleIntent, options.toBundle());
        } else {
            startActivity(articleIntent);
        }
    }

    /*
     * Class to deal with the API call asynchronously
     */
    private class ApiTask extends AsyncTask<Void, Void, Article[]> {
        @Override
        protected void onPreExecute() {
            // Instantiate progress bar while async task is executing in background
            ViewGroup root = findViewById(R.id.overviewRootLayout);
            View view = LayoutInflater.from(ActivityOverview.this).inflate(R.layout.progress_bar_news_list, root);
        }

        @Override
        protected Article[] doInBackground(Void... voids) {
            return NewsApi.get();
        }

        @Override
        protected void onPostExecute(Article[] result) {
            // Remove progress bar after async task has finished
            ViewGroup root = findViewById(R.id.overviewRootLayout);
            ProgressBar p = findViewById(R.id.newsListProgressBar);
            root.removeView(p);
        }
    }
}
