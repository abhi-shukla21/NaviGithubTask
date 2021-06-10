package com.example.navigithubtask;

import android.util.Log;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    private static final String TAG = DateTimeUtil.class.getSimpleName();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static PrettyTime prettyTime = new PrettyTime();

    public static String getPrettyTime(String time){
        Log.d(TAG, "parsing: " + time);
        try {
            Date date = sdf.parse(time);
            return prettyTime.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
