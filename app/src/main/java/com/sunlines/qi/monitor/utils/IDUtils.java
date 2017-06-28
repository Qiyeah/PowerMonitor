package com.sunlines.qi.monitor.utils;

/**
 * Created by temporary on 2017-01-06.
 */

public class IDUtils {
    public static long generateId(){
        String str = "";
        for (int i = 0; i < 18; i++) {
            str+=(int)(Math.random()*9 + 1);
        }
        return Long.parseLong(str);
    }
}
