package kz.anet.goal_trackingapp.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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


    public static int getIntMonthFromDate(String finishedAtDate) {
        int returnedValue;
        int forthIndex = Character.getNumericValue(finishedAtDate.charAt(4));
        if(finishedAtDate.charAt(3) == '0'){
            returnedValue = forthIndex;
        }else{
            returnedValue = 10 + forthIndex;
        }
        return returnedValue;
    }

    public static List<Date> getListOfMonthAsDate(){
        List<Date> dates = new ArrayList<>();
        for(int i = 1; i <= 12; i++){
            Calendar c = Calendar.getInstance();
            c.set(2000,i,1);
            dates.add(c.getTime());
        }
        return dates;

    }

    public static String[] getMonthArray(){
        return new String[]{
                "jan",
                "feb",
                "mar",
                "apr",
                "may",
                "jun",
                "jul",
                "aug",
                "sep",
                "oct",
                "nov",
                "dec"
        };
    }
    public static String getMonthTitleFromNumber(double value) {
        switch ((int)value){
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
            default:
                return "";
        }
    }
}
