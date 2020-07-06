package com.fh.shop.api.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
    public static final String Y_M_D = "yyyy-MM-dd";

    public static final String FULL_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyyMMhhmmss="yyyyMMddHHmmss";


    public static Date addMinutes(Date date,int minute){
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.MINUTE,minute);
        Date time = instance.getTime();
        return time;
    }

    public static  String getYyyyMMhhmmss(Date date,String pattern){
        if(date != null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String result = simpleDateFormat.format(date);
            return result;
        }
        return "";
    }
    public static Long dayBetween(String s1, String s2, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date d1 = sdf.parse(s1);
            Date d2 = sdf.parse(s2);
            return (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public static List<String> dayList(String s1, String s2, String pattern) {
        Long aLong = dayBetween(s1, s2, pattern);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        List<String> days = new ArrayList<>();
        try {
            Date parse = sdf.parse(s1);
            Calendar instance = Calendar.getInstance();
            for (int i = 0; i <= aLong; i++) {
                instance.setTime(parse);
                instance.add(Calendar.DAY_OF_YEAR, i);
                Date time = instance.getTime();
                String format = sdf.format(time);
                days.add(format);
            }
            return days;
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test() throws ParseException {
       /* String date = "2020-01-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sdf.parse(date);
        System.out.println(parse);
        Calendar instance = Calendar.getInstance();
        instance.setTime(parse);
        instance.add(Calendar.DAY_OF_YEAR, 33);
        System.out.println(instance);
        Date time = instance.getTime();
        System.out.println(time);
        String format = sdf.format(time);*/
        List<String> stringList = DateUtil.dayList("2020-04-01", "2020-04-10", DateUtil.Y_M_D);

        System.out.println(stringList);
    }

    public static Date str(String date, String pattern) {
        if (StringUtils.isEmpty(date)) {
            throw new RuntimeException("日期格式的字符串为空");
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date d = null;
        try {
            d = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    public static String date(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String format = sdf.format(date);
        return format;
    }
}
