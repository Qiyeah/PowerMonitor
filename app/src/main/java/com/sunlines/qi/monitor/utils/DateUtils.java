package com.sunlines.qi.monitor.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by temporary on 2017-01-05.
 */

public class DateUtils {
    public static String getDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String date = df.format(new Date());
        if (null != date && !"".equals(date)){
            return date;
        }
        return "";
    }
    public static String formatDate(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String date1 = df.format(date);
        if (null != date1 && !"".equals(date1)){
            return date1;
        }
        return "";
    }
    public static Date parse2Date(String date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        try {
            return df.parse(date);
        } catch (ParseException e) {
            //e.printStackTrace();
        }
        return null;
    }
    public static long date2Long(Date date){
        return date.getTime();
    }
    public static long date2Long(){
        return new Date().getTime();
    }
    public static long date2Long(String date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        try {
            return df.parse(date).getTime();
        } catch (ParseException e) {
            //e.printStackTrace();
        }
        return -1;
    }
}
