package org.maxcrone.news.articles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ArticleContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<NewsArticle> ITEMS = new ArrayList<NewsArticle>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, NewsArticle> ITEM_MAP = new HashMap<String, NewsArticle>();

    private static final int COUNT = 8;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(NewsArticle item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static NewsArticle createDummyItem(int position) {
        String title = "Impeach Trump: why Republicans, not Democrats are talking up the prospect";
        String date = "August 26, 2018";
        String src = "The Guardian";
        String text = "By one count the president has nine impeachable offenses to his name but the politics of removing him from office are complicated\n" +
                "\n" +
                "Donald Trump and the Republicans want to talk about his impeachment and removal from office. Democrats would really rather not.";
        return new NewsArticle(String.valueOf(position), title, date, src, text);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    public static class NewsArticle {
        public final String id;
        public final String title;
        public final String date;
        public final String newsSource;
        public final String text;

        public NewsArticle(String id, String title, String date, String newsSource, String text) {
            this.id = id;
            this.title = title;
            this.date = date;
            this.newsSource = newsSource;
            this.text = text;
        }

        @Override
        public String toString() {
            String str = this.title + " - " + this.newsSource;
            return str;
        }
    }
}
