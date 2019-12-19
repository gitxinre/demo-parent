package com.ly.demo.common.util;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * 基于joda-time进行实现日期时间工具类
 *
 * @author xinre
 */
public class DateUtils {

    private static final String Y_M_D_H_M_S_MS_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String Y_M_D_H_M_S_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String Y_M_D_FORMAT = "yyyy-MM-dd";
    public static final String E_FORMAT = "E";


    public static void main(String[] args) {
        DateUtils dateUtils = new DateUtils();
        dateUtils.test001();
        dateUtils.test002();
        dateUtils.test003();
        dateUtils.test004();
        dateUtils.test005();
        dateUtils.test006();

        String s = currentTimeFormat();
        System.out.println("s = " + s);
    }

    public void test001() {
        DateTime dateTime = new DateTime(1988, 1, 1, 15, 2, 8, 258);
        System.out.println("dateTime = " + dateTime.toString("E MM/dd/yyyy HH:mm:ss.SSS"));
        System.out.println("dateTime = " + dateTime.toString("E"));
        System.out.println("dateTime = " + dateTime.toString("MM/dd/yyyy HH:mm:ss.SSS"));
        System.out.println("dateTime = " + dateTime.toString("E yyyy-MM-dd HH:mm:ss.SSS"));
        System.out.println("dateTime = " + dateTime.toString(Y_M_D_H_M_S_MS_FORMAT));
        System.out.println("dateTime = " + dateTime.toString("yyyy-MM-dd hh:mm:ss.SSS"));
        System.out.println("dateTime = " + dateTime.toString(Y_M_D_H_M_S_FORMAT));
        System.out.println("dateTime = " + dateTime.toString("yyyy-MM-dd hh:mm:ss"));
    }

    public void test002() {
        LocalDate ld1 = new LocalDate(2020, 1, 1);
        LocalDate ld2 = new LocalDate(2021, 1, 1);
        int days = Days.daysBetween(ld1, ld2).getDays();
        System.out.println("days = " + days);

        DateTime dateTime = new DateTime();
        String string = dateTime.toString();
        System.out.println("string = " + string);
        System.out.println("dateTime = " + dateTime.toString("E yyyy-MM-dd HH:mm:ss.SSS"));
        System.out.println("dateTime = " + dateTime.plusDays(1).toString("E yyyy-MM-dd HH:mm:ss.SSS"));
        System.out.println("dateTime = " + dateTime.plusDays(2).toString("E yyyy-MM-dd HH:mm:ss.SSS"));
        System.out.println("dateTime = " + dateTime.plusDays(3).toString("E yyyy-MM-dd HH:mm:ss.SSS"));
        System.out.println("dateTime = " + dateTime.plusDays(4).toString("E yyyy-MM-dd HH:mm:ss.SSS"));
        System.out.println("dateTime = " + dateTime.plusDays(5).toString("E yyyy-MM-dd HH:mm:ss.SSS"));
        System.out.println("dateTime = " + dateTime.plusDays(6).toString("E yyyy-MM-dd HH:mm:ss.SSS"));


        DateTime now = DateTime.now();
        //DateTimeFormat dtf = new DateTimeFormat("");

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");

        DateTime parse = DateTime.parse("19910427050208", dateTimeFormatter);
        System.out.println("parse = " + parse.toString("yyyy-MM-dd HH:mm:ss"));
        Date date = parse.toDate();
        System.out.println("date = " + date);


        java.sql.Date date1 = new java.sql.Date(parse.getMillis());
        System.out.println("date1 = " + date1);
        Date date2 = new Date(parse.getMillis());
        System.out.println("date2 = " + date2);
        System.out.println("=====================================================================");
        System.out.println("parse = " + parse);
        System.out.println("parse.getCenturyOfEra() = " + parse.getCenturyOfEra());
        System.out.println("parse.getChronology() = " + parse.getChronology());
        System.out.println("parse.getDayOfMonth() = " + parse.getDayOfMonth());
        System.out.println("parse.getDayOfWeek() = " + parse.getDayOfWeek());
        System.out.println("parse.getDayOfYear() = " + parse.getDayOfYear());
        System.out.println("parse.getEra() = " + parse.getEra());
        System.out.println("parse.getHourOfDay() = " + parse.getHourOfDay());
        System.out.println("parse.getMillis() = " + parse.getMillis());
        System.out.println("parse.getMillisOfDay() = " + parse.getMillisOfDay());
        System.out.println("parse.getMillisOfSecond() = " + parse.getMillisOfSecond());
        System.out.println("parse.getMinuteOfDay() = " + parse.getMinuteOfDay());
        System.out.println("parse.getMinuteOfHour() = " + parse.getMinuteOfHour());
        System.out.println("parse.getMonthOfYear() = " + parse.getMonthOfYear());
        System.out.println("parse.getSecondOfDay() = " + parse.getSecondOfDay());
        System.out.println("parse.getSecondOfMinute() = " + parse.getSecondOfMinute());
        System.out.println("parse.getWeekOfWeekyear() = " + parse.getWeekOfWeekyear());
        System.out.println("parse.getWeekyear() = " + parse.getWeekyear());
    }

    public void test003() {
        DateTime dateTime = new DateTime(System.currentTimeMillis());
        System.out.println("dateTime-Y.toString(\"E yyyy-MM-dd HH:mm:ss:SSS\") = " + dateTime.toString("E yyyy-MM-dd HH:mm:ss:SSS"));
        DateTime dateTime1 = dateTime.plusMillis(5);
        System.out.println("dateTime-N.toString(\"E yyyy-MM-dd HH:mm:ss:SSS\") = " + dateTime1.toString("E yyyy-MM-dd HH:mm:ss:SSS"));
    }

    public void test004() {
        DateTime dt = new DateTime();
        String string = dt.dayOfWeek().withMaximumValue().toString("E yyyy-MM-dd HH:mm:ss:SSS");
        System.out.println("string = " + string);
        DateTime.Property property = dt.dayOfWeek();
        DateTime plus = dt.plusMillis(1000);
        System.out.println("dt = " + dt.toString("E yyyy-MM-dd HH:mm:ss:SSS"));
        System.out.println("plus = " + plus.toString("E yyyy-MM-dd HH:mm:ss:SSS"));

        Instant instant = new Instant();
        String instantZone = instant.getZone().toString();
        System.out.println("instantZone = " + instantZone);

        System.out.println("instant = " + instant);
        DateTime dt1 = new DateTime();
        System.out.println("dt1 = " + dt1);
        String dt1Zone = dt1.getZone().toString();
        System.out.println("dt1Zone = " + dt1Zone);
    }

    public void test005() {
        //SystemFactory.getClock().getDateTime();
        //SystemClock

        DateTime dt = new DateTime(2019, 12, 2, 5, 2, 8, 899);

        DateTime dt1 = new DateTime(new Date());

        LocalDate localDate = new LocalDate();
        System.out.println("localDate = " + localDate.toString("E yyyy-MM-dd"));
    }

    public void test006() {
        DateTime dt1 = DateTime.now();
        System.out.println("dt-Y = " + dt1.toString("yyyy-MM-dd HH:mm:ss:SSS"));

        DateTime dt2 = dt1
                .plusYears(0)
                .plusMonths(0)
                //.plusWeeks(0)
                .plusDays(5)
                .plusHours(0)
                .plusMinutes(0)
                .plusSeconds(5)
                .plusMillis(0);
        System.out.println("dt-N = " + dt2.toString("yyyy-MM-dd HH:mm:ss:SSS"));
        String timeIntervalFormat = durationFormat(dt1, dt2);
        System.out.println("timeIntervalFormat = " + timeIntervalFormat);
    }

    public static Date currentTime() {
        return new Date();
    }

    /**
     * 获取当前时间（年-月-日 时:分:秒.毫秒）
     *
     * @return 当前时间
     */
    public static String currentTimeFormat() {
        return currentTimeFormat(Y_M_D_H_M_S_MS_FORMAT);
    }

    public static String currentTimeFormat(String format) {
        return DateTime.now().toString(format);
    }

    public static String dateFormat(Date date) {
        return dateFormat(date, Y_M_D_H_M_S_MS_FORMAT);
    }

    public static String dateFormat(Date date, String format) {
        return new DateTime(date.getTime()).toString(format);
    }

    public static Date string2Date(String date) {
        return string2Date(date, Y_M_D_H_M_S_MS_FORMAT);
    }

    public static Date string2Date(String date, String format) {
        return DateTime.parse(date, DateTimeFormat.forPattern(format)).toDate();
    }


    /**
     * 格式化时间间隔
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 间隔结果格式化
     */
    public static String durationFormat(Date start, Date end) {
        return durationFormat(new DateTime(start.getTime()), new DateTime(end.getTime()));
    }

    /**
     * 格式化时间间隔
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 间隔结果格式化
     */
    private static String durationFormat(DateTime start, DateTime end) {
        return doDurationFormat(new Period(start, end));
    }

    private static String doDurationFormat(Period period) {
        int count = 0;
        int ms = period.getMillis();
        int s = period.getSeconds();
        int m = period.getMinutes();
        int h = period.getHours();
        int d = period.getDays() + period.getWeeks() * 7;
        int M = period.getMonths();
        int y = period.getYears();
        StringBuilder timeInterval = new StringBuilder();

        if (y != 0) {
            timeInterval.append(y).append("年");
            count += 6;
        }
        if (M != 0) {
            timeInterval.append(M).append("个月");
            count += 5;
        }
        if (d != 0) {
            timeInterval.append(d).append("天");
            count += 4;
        }
        if (count > 4) {
            return timeInterval.toString();
        }
        if (h != 0) {
            timeInterval.append(h).append("小时");
            count += 3;
        }
        if (count > 3) {
            return timeInterval.toString();
        }
        if (m != 0) {
            timeInterval.append(m).append("分钟");
            count += 2;
        }
        if (count > 2) {
            return timeInterval.toString();
        }
        if (s != 0) {
            timeInterval.append(s).append("秒");
            count += 1;
        }
        if (count >= 1) {
            return timeInterval.toString();
        }
        if (ms != 0) {
            timeInterval.append(ms).append("毫秒");
            return timeInterval.toString();
        } else {
            return "0毫秒";
        }
    }

}
