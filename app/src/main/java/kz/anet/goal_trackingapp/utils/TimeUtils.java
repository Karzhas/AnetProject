package kz.anet.goal_trackingapp.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    public static String getFormattedDate(int day, int month, int year){
        String fday = day < 10 ? "0" + day : day + "";
        String fmonth = month < 10 ? "0" + month : month + "";
        return fday + "/" + fmonth + "/" + year;
    }
    public static String getFormattedTime(int minute, int hour){
        String fminute = minute < 10 ? "0" + minute : minute + "";
        String fhour = hour < 10 ? "0" + hour : hour + "";
        return fhour + ":" + fminute;
    }
    public static Date getDateFromString(String time, String date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy, hh:mm");
        //Log.d("mhmh", form)
        Date parsedDate = null;
        try {
            parsedDate = formatter.parse(date + ", " + time);
            Log.d("mhm", parsedDate.toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedDate;
    }


}
