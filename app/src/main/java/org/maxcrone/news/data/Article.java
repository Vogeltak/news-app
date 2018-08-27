package org.maxcrone.news.data;

/*
 * The Article class represents a single article
 */
public class Article {
    private String title;
    private String src;
    private String text;
    private String date;
    private String url;
    private String imgUrl;

    public Article(String title, String src, String text, String date, String url, String imgUrl) {
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

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
