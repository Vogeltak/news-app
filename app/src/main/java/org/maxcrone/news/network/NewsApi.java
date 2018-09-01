package org.maxcrone.news.network;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.maxcrone.news.R;
import org.maxcrone.news.activities.ActivityOverview;
import org.maxcrone.news.data.Article;
import org.maxcrone.news.util.CacheOperations;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsApi {
    private final String url = "http://news.dagdro.men/api/v1/articles";
    private OkHttpClient client = new OkHttpClient();
    private Context context;


    public NewsApi(Context context) {
        this.context = context;
    }

    /*
     * Retrieves the article data
     * Checks if a cache already exists, otherwise it sends a web request to retrieve the data
     * from the web api.
     */
    public Article[] get() {
        Article[] data;

        if (CacheOperations.cacheExists(context)) {
            data = getArticlesFromCache();
            System.out.println("[NEWS API] retrieving articles from cache");
        } else {
            try {
                data = new ApiTask(this)
                        .execute()
                        .get();
            } catch (Exception e) {
                System.out.println(e.toString());

                data = new Article[0];
            }
            System.out.println("[NEWS API] retrieving articles from web");
        }

        return data;
    }

    public Article[] getArticlesFromWeb() {
        Request req = new Request.Builder()
                .url(url)
                .build();

        try (Response res = client.newCall(req).execute()) {
            String jsonResponse = res.body().string();

            // Cache the response
            CacheOperations.createCache(context, jsonResponse);

            return getArticlesFromResponse(jsonResponse);
        } catch (Exception e) {
            System.out.println(e);
        }

        return new Article[0];
    }

    private Article[] getArticlesFromCache() {
        try {
            String jsonResponse = CacheOperations.readCache(context);
            Article[] articles = getArticlesFromResponse(jsonResponse);
            return articles;
        } catch (Exception e) {
            System.out.println(e.toString());

            // Failed to read file, thus return empty array
            return new Article[0];
        }
    }

    private static Article[] getArticlesFromResponse(String response) throws JSONException {
        // Load response into a json array
        JSONArray jsonArray = new JSONArray(response);

        // Create the array with Article objects
        Article[] articles = new Article[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject record = jsonArray.getJSONObject(i);

            String title = record.getString("title");
            String src = record.getString("source");
            String text = record.getString("text").replaceAll("<br>", "\n");
            String url = record.getString("url");
            String imgUrl = record.getString("img");

            // Parse the time to a Date object
            String date = record.getString("date");
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateObject;

            try {
                dateObject = format.parse(date);
            } catch (ParseException e) {
                System.out.println(e.toString());
                Calendar today = Calendar.getInstance();
                today.set(Calendar.HOUR_OF_DAY, 0);
                dateObject = today.getTime();
            }

            articles[i] = new Article(title, src, text, dateObject, url, imgUrl);
        }

        return articles;
    }

    /*
     * Class to deal with the API call asynchronously
     */
    private class ApiTask extends AsyncTask<Void, Void, Article[]> {
        private NewsApi newsApi;

        public ApiTask(NewsApi newsApi) {
            this.newsApi = newsApi;
        }

        @Override
        protected Article[] doInBackground(Void... voids) {
            return newsApi.getArticlesFromWeb();
        }
    }
}
