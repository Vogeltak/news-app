package org.maxcrone.news.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;

public class CacheOperations {
    public static final String FILE_NAME = "articles.json";

    /*
     * Determine if their exists a relevant cache file of the articles
     */
    public static boolean cacheExists(Context c) {
        File file = new File(c.getCacheDir(), FILE_NAME);

        return file.isFile();
    }

    /*
     * Read the contents of the cache file
     */
    public static String readCache(Context c) throws IOException {
        File file = new File(c.getCacheDir(), FILE_NAME);
        FileInputStream fis = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }

        reader.close();

        return sb.toString();
    }

    /*
     * Create new cache file
     */
    public static void createCache(Context c, String content) throws IOException {
        // First wipe the cache
        wipeCache(c);

        // Create new cache file
        File file = new File(c.getCacheDir(), FILE_NAME);

        // Write content to the cache file
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.close();

        System.out.println("[CACHE] created file " + file.getAbsolutePath());
    }

    /*
     * Wipe all cache files for this application
     */
    public static void wipeCache(Context c) {
        File[] fileList = c.getCacheDir().listFiles();

        for (File f : fileList) {
            f.delete();
        }
    }
}
