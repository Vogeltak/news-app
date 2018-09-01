package org.maxcrone.news.network;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import org.maxcrone.news.R;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImageTask extends AsyncTask<String, Void, Bitmap> {
    private File cacheDir;
    //private OkHttpClient client = new OkHttpClient.Builder().cache(new Cache(cacheDir, 10*1024*1024)).build();
    private OkHttpClient client = new OkHttpClient();
    private ImageResponse delegate = null;

    public interface ImageResponse {
        void processImage(Bitmap result);
    }

    public ImageTask(File cacheDir, ImageResponse delegate) {
        this.cacheDir = cacheDir;
        this.delegate = delegate;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        /*Request req = new Request.Builder()
                .url(urls[0])
                .build();

        try {
            Response res = client.newCall(req).execute();
            InputStream is = res.body().byteStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        try {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        delegate.processImage(result);
    }
}
