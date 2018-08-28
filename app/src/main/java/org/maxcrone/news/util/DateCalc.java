package org.maxcrone.news.util;

import java.util.Calendar;
import java.util.Date;

public class DateCalc {

    public static int getAgeInHours(Date d) {
        Calendar today = Calendar.getInstance();

        long diff = today.getTimeInMillis() - d.getTime();
        int diffDays = (int) (diff / (1000*60*60));

        return diffDays;
    }

}
