
package com.utopia.watchout.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateHelper {

    public final static String KOR_FORMAT = "yyyy/MM/dd  hh:mm:ss";

    public static String getDate(long milliSeconds, String dateFormat, Locale locale)
    {
        DateFormat formatter = new SimpleDateFormat(dateFormat, locale);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

}
