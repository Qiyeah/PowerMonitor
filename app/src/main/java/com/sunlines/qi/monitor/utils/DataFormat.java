package com.sunlines.qi.monitor.utils;

import java.text.DecimalFormat;

/**
 * Created by temporary on 2017-01-16.
 */

public class DataFormat {
    public static String float2Str(float data) {
        String value = String.valueOf(data);
//        if (value.toString().matches("[0-9]*\\.[0-9]*")) {
        int len = value.length();
        switch (len) {
            case 3:
                return  new String(value + "00");
            case 4:
                return new String(value + "0");
            default:
                return new DecimalFormat("#.000").format(data);
        }
//        } else if (value.toString().matches("[0-9]*")){
//            value = new String(value + ".000");
//        }
    }
    public static String double2Str(double value) {
        DecimalFormat df = new DecimalFormat("0.000");
        if (100.0f > value) {
            return df.format(value);
        } else if (100.0f <= value && 1000.0f >= value) {
            return df.format(value);
        } else if (1000.0f <= value && 10000.0f >= value) {
            return df.format(value);
        }else if (10000.0f < value && 10000.0f * 10000.0f >= value) {
            return df.format(value / 10000.0f) + " 万";
        } else if (10000 * 10000 < value) {
            return df.format(value / (10000.0f*10000.0f)) + " 亿";
        }
        return "0.000";
    }
}
