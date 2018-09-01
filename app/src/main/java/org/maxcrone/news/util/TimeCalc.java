package org.maxcrone.news.util;

import java.util.Calendar;
import java.util.Date;

public class TimeCalc {

    public static int getAgeInHours(Date d) {
        Calendar today = Calendar.getInstance();

        long diff = today.getTimeInMillis() - d.getTime();
        int diffDays = (int) (diff / (1000*60*60));

        return diffDays;
    }

    public static int getEstimatedReadingTime(String text) {
        int nWords = 0;

        if (!text.isEmpty()) {
            nWords = text.split("\\s+").length;
        }

        int estimatedReadingTimeInMinutes = (int) (nWords / 150);

        return estimatedReadingTimeInMinutes;
    }

}
