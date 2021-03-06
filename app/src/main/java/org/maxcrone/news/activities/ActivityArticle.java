package org.maxcrone.news.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.maxcrone.news.R;
import org.maxcrone.news.data.Article;
import org.maxcrone.news.network.ImageTask;
import org.maxcrone.news.util.TimeCalc;

import java.net.URL;
import java.net.URLConnection;

public class ActivityArticle extends AppCompatActivity implements ImageTask.ImageResponse{
    private Article article;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Toolbar articleToolbar = findViewById(R.id.articleToolbar);
        setSupportActionBar(articleToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Disable automatic title, because we use a TextView as title for customization purposes.
        ab.setDisplayShowTitleEnabled(false);

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        // Retrieve Article object from the Intent Extra
        Intent intent = getIntent();
        article = (Article) intent.getSerializableExtra(ActivityOverview.ARTICLE_OBJECT);

        // Retrieve all View objects for the elements of the article
        TextView sourceView = findViewById(R.id.articleSource);
        TextView titleView = findViewById(R.id.articleTitle);
        TextView ertView = findViewById(R.id.articleERT);
        TextView textView = findViewById(R.id.articleText);

        // Retrieve article image
        try {
            new ImageTask(getCacheDir(), this)
                    .execute(article.getImgUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert all relevant text
        sourceView.setText(article.getSrc());
        titleView.setText(article.getTitle());
        ertView.setText(TimeCalc.getEstimatedReadingTime(article.getText()) + " minutes");
        textView.setText(article.getText());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article, menu);

        MenuItem shareItem = menu.findItem(R.id.shareButton);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        // Create shareIntent
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, article.getUrl());
        shareIntent.setType("text/plain");

        // Set the shareIntent to the ShareActionProvider
        //setShareIntent(shareIntent);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        // Handle item selection
        switch (menuItem.getItemId()) {
            case R.id.browserButton:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
                startActivity(browserIntent);
                return true;
            case R.id.shareButton:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, article.getUrl());
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.share_article)));
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void setShareIntent(Intent shareintent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareintent);
        }
    }

    @Override
    public void processImage(Bitmap result) {
        ImageView imgView = findViewById(R.id.articleImg);
        imgView.setImageBitmap(result);
    }
}
