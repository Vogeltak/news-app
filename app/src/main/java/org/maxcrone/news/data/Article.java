package org.maxcrone.news.data;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

/*
 * The Article class represents a single article
 */
public class Article implements Serializable, Comparable<Article> {
    private String title;
    private String src;
    private String text;
    private Date date;
    private String url;
    private String imgUrl;

    public Article(String title, String src, String text, Date date, String url, String imgUrl) {
        this.title = title;
        this.src = src;
        this.text = text;
        this.date = date;
        this.url = url;
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getSrc() {
        return src;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    @Override
    public int compareTo(@NonNull Article o) {
        if (date.after(o.date)) {
            return -1;
        } else if (date.before(o.date)) {
            return 1;
        }

        return 0;
    }
}
