package utils;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {

    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private final static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public static java.sql.Date parseYearDateFromStr(String year) {

        Date yearDate = Date.valueOf(year);

        return yearDate;

    }

    public static String parseYearDateToStr(java.sql.Date year){

        long time = year.getTime();

        return dateFormat.format(new Date(time));
    }

    public static Time parseTimeFromStr(String timeStr){

        java.util.Date time = new java.util.Date();

        try {
            time =  timeFormat.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Time(time.getTime());

    }

    public static String parseTimeToStr(Time time){

        return time.toString();
    }
}
