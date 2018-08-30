package com.obm.hy.export.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by and on 2017-08-22.
 */

public class DateUtil {

    public  static  String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String date = sdf.format(new Date());
        return date;
    }

    public static Date transForDate(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Date date = new Date();
        try {
            date=sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getTime(Date date,String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);//设置日期格式
            return sdf.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "";
    }

}
