package org.maxcrone.news;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.maxcrone.news.articles.ArticleContent;

public class ActivityOverview extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {
    public static final String EXTRA_MESSAGE = "org.maxcrone.news.article";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        Toolbar newsToolbar = findViewById(R.id.newsToolbar);
        setSupportActionBar(newsToolbar);

        // Disable automatic title, because we use a TextView as title for customization purposes.
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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

    @Override
    public void onListFragmentInteraction(ArticleContent.NewsArticle item) {
        Intent articleIntent = new Intent(this, ActivityArticle.class);
        startActivity(articleIntent);
    }
}
