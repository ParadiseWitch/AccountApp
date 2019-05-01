package com.example.administrator.accounapp;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    //util time -> HH:mm
    public static String getFormattedTime(long timeStamp){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(new Date(timeStamp));

    }
    //得到今天日期
    //util time(今天日期) -> yyyy-MM-dd
    public static String getFormattedDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date());
    }
    //yyyy-MM-dd  -> Date类
    public static Date strDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
    //Date类  ->  星期几
    public static String getWeekDay (String date){
        String[] weekdays = {"SUN","MON","TUE","WED","THU","FRI","SAT"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(strDate(date));
        int index = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekdays[index];
    }
    //yyyy-MM-dd   ->  Jun 12
    public static String getDateTitle(String date){
        String[] months = {"Jan.","Feb.","March","April","June","July","August","Sep.","Oct.","Nov.","Dec."};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(strDate(date));
        int monthIndex = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return months[monthIndex] + " " + String.valueOf(day);
    }

}
