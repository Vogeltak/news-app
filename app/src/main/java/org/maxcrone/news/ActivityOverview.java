package org.maxcrone.news;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

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

        // Initialize an adapter
        mAdapter = new NewsListAdapter();
        mRecyclerView.setAdapter(mAdapter);

        // Initialize divider item decoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), 1);
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
}
