package org.maxcrone.news.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.maxcrone.news.R;
import org.maxcrone.news.data.Article;
import org.maxcrone.news.util.DateCalc;

public class ActivityArticle extends AppCompatActivity {

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
        Article article = (Article) intent.getSerializableExtra(ActivityOverview.ARTICLE_OBJECT);

        // Retrieve all View objects for the elements of the article
        TextView sourceView = findViewById(R.id.articleSource);
        TextView titleView = findViewById(R.id.articleTitle);
        TextView ageView = findViewById(R.id.articleAge);
        TextView textView = findViewById(R.id.articleText);

        // Insert all relevant texts
        sourceView.setText(article.getSrc());
        titleView.setText(article.getTitle());
        ageView.setText(String.valueOf(DateCalc.getAgeInHours(article.getDate())) + " hours ago");
        textView.setText(article.getText());
    }
}
