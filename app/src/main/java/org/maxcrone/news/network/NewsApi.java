package org.maxcrone.news.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.maxcrone.news.data.Article;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsApi {
    private static final String url = "http://news.dagdro.men/api/v1/articles";
    private static OkHttpClient client = new OkHttpClient();

    private NewsApi() {}

    public static Article[] get() {
        Request req = new Request.Builder()
                .url(url)
                .build();

        try (Response res = client.newCall(req).execute()) {
            return getArticlesFromResponse(res.body().string());
        } catch (Exception e) {
            System.out.println(e);
        }

        return new Article[0];
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
}
