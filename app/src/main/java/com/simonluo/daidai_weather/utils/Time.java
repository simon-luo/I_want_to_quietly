package com.simonluo.daidai_weather.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 333 on 2016/4/1.
 */
public class Time {
    @SuppressLint("SimpleDateFormat")
    public static String getNowYMDHMSTime(){
        SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = mDateFormat.format(new Date());
        return date;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getNowMDHMSTime(){
        SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
        String date = mDateFormat.format(new Date());
        return date;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getNowYMDTime(){
        SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = mDateFormat.format(new Date());
        return date;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getYMD(Date date){
        SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return mDateFormat.format(date);
    }
}
