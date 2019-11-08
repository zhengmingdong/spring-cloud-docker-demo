package com.zynn.common.core.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author lk
 * @date 2018年06月12日
 */
public class DateUtils {

    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

    public static final String yyyy_MM_dd = "yyyy-MM-dd";

    public static final String yyyy_MM = "yyyy-MM";

    public static final String format_hour_date_time = "HH:mm:ss";

    /**
     * 2个String类型的日期进行比较（天）
     *
     * @param beginString 时间
     * @param endString   比较时间
     * @return 是否小于比较时间
     */
    public static boolean isBefore(String beginString, String endString) {
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd);
        boolean status = false;
        try {
            Date bt = sdf.parse(beginString);
            Date et = sdf.parse(endString);
            if (bt.before(et)) {
                status = true;
            }
        } catch (Exception e) {

        }
        return status;
    }

    /**
     * 2个String类型的日期进行比较（天）
     *
     * @param beginString 时间
     * @param endString   比较时间
     * @return 是否小于比较时间
     */
    public static boolean isAfter(String beginString, String endString) {
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
        boolean status = false;
        try {
            Date bt = sdf.parse(beginString);
            Date et = sdf.parse(endString);
            if (bt.after(et)) {
                status = true;
            }
        } catch (Exception e) {
        }
        return status;
    }

    public static String format(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date formatDate(String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd);
        Date date = null;
        try {
            date = sdf.parse(pattern);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
        return sdf.format(date);
    }

    /**
     * 10位时间戳和13位时间戳格式
     *
     * @param str_num 时间字符串
     * @param format  时间格式
     * @return string型的时间
     * @author 刘凯
     * @date 2018-07-01
     */
    public static String timestamp2Date(String str_num, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (str_num.length() == 13) {
            String date = sdf.format(new Date(Long.parseLong(str_num)));
            return date;
        } else {
            String date = sdf.format(new Date(Integer.parseInt(str_num) * 1000L));
            return date;
        }
    }

    /**
     * 根据系统时间获取昨天日期
     *
     * @return 昨天Date
     */
    public static Date getYesterday() {
        DateFormat df = new SimpleDateFormat(yyyy_MM_dd);
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        //把日期往后增加一天.整数往后推,负数往前移动
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 获取当期日期前day天的日期
     * @param day
     * @return
     */
    public static Date getAppointday(int day) {
        DateFormat df = new SimpleDateFormat(yyyy_MM_dd);
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        //把日期往后增加一天.整数往后推,负数往前移动
        calendar.add(Calendar.DATE, -day);
        return calendar.getTime();
    }

    public static String getYesterdayToString(){
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd);
        return sdf.format(getYesterday());
    }

    public static String getAppointdayToString(int day){
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd);
        return sdf.format(getAppointday(day));
    }

    public static String getNowDate(){
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd);
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return sdf.format(calendar.getTime());
    }

    public static String getNowDateStrStart() {
        return String.format("%s %s", getNowDate(), "00:00:00");
    }

    public static String getNowDateStrEnd() {
        return String.format("%s %s", getNowDate(), "23:59:59");
    }

    public static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return sdf.format(calendar.getTime());
    }


    /**
     * 1:周日  2：周一  3：周二
     * @return
     */
    public static int getWeekDay(){
        Date today = new Date();
        Calendar c=Calendar.getInstance();
        c.setTime(today);
        int weekday=c.get(Calendar.DAY_OF_WEEK);
        return weekday;
    }


    /**
     * 根据系统时间获取明天日期
     *
     * @return 昨天Date
     */
    public static Timestamp getTomorrow(Timestamp timestamp) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(timestamp.getTime()));
        //把日期往后增加一天.整数往后推,负数往前移动
        calendar.add(Calendar.DATE, 1);
        return new Timestamp(calendar.getTime().getTime());
    }

    public static Date parse(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date parse(String dateString,String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate 时间格式字符串
     * @return date
     * @author wangyulin
     */
    public static Timestamp millsToTimestamp(String strDate) {

        Timestamp ts = null;
        String tsStr = strDate;
        try {
            ts = new Timestamp(Long.valueOf(strDate));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ts;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate 时间格式字符串
     * @return date
     * @author wangyulin
     */
    public static Date millsToDate(String strDate) {

        Date ts = null;
        String tsStr = strDate;
        try {
            ts = new Date(Long.valueOf(strDate));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ts;
    }


    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate 时间格式字符串
     * @return date
     * @author wangyulin
     */
    public static String millsToDateString(String strDate) {

        Timestamp ts = millsToTimestamp(strDate);

        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
        return sdf.format(ts);
    }


    /**
     * 将格式字符串转换为时间 yyyy-MM-dd
     *
       * @param strDate 时间格式字符串
     * @return date
     * @author 刘凯
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(yyyy_MM_dd);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将时间转为十分秒
     *
     * @param ms
     * @return
     */
    public static String formatTimeForDate(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        sb.append(day + ":");
        sb.append(hour + ":");
        sb.append(minute + ":");
        sb.append(second + ":");
        return sb.toString();
    }

    /**
     * 算昨天凌晨00:00:00 毫秒值
     */
    public static Long yesterdayWeeHours() {
        long nowTime = System.currentTimeMillis();
        Long day = nowTime / (24 * 60 * 60 * 1000);
        Long yesterdayWeeHours = (day - 1) * 24 * 60 * 60 * 1000 - 8 * 60 * 60 * 1000;
        return yesterdayWeeHours;
    }

    /**
     * 算今天凌晨00:00:00 毫秒值
     */
    public static Long getTodayWeeHours() {
        //现在的时间戳 补齐时间戳起始时间为8点的差值
        long nowTime = System.currentTimeMillis() + 8 * 60 * 60 * 1000;
        //算出天数
        Long day = nowTime / (24 * 60 * 60 * 1000);
        Long todayWeeHours = (day) * 24 * 60 * 60 * 1000 - 8 * 60 * 60 * 1000;
        return todayWeeHours;
    }

    /**
     * 将毫秒值转化成 String格式
     *
     * @param time 时间毫秒值
     * @return 返回字符串 格式 yyyy_MM_dd_HH_mm_ss
     */
    public static String formatDate(Long time) {
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
        Date date;
        try {
            date = sdf.parse(sdf.format(time));
            return sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 计算两个日期的差值--天
     *
     * @param oldDate
     * @param newDate
     * @return
     */
    public static int daysDifferInDay(Date oldDate, Date newDate) {

        Calendar oldCal = Calendar.getInstance();
        oldCal.setTime(oldDate);

        Calendar newCal = Calendar.getInstance();
        newCal.setTime(newDate);
        int day1 = oldCal.get(Calendar.DAY_OF_YEAR);
        int day2 = newCal.get(Calendar.DAY_OF_YEAR);

        return day2 - day1;
    }

    /**
     * 获取当前日期所在一周的开始日期和结束日期
     *
     * @param date
     * @return [0]-开始日期、[1]-结束日期
     */
    public static String[] getWeekInfo(String date) {
        LocalDate inputDay = LocalDate.parse(date);
        //一周的第一天
        TemporalAdjuster FIRST_OF_WEEK = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.minusDays(localDate.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue()));
        LocalDate firstDay = inputDay.with(FIRST_OF_WEEK);
        //一周的最后一天
        TemporalAdjuster LAST_OF_WEEK = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.plusDays(DayOfWeek.SUNDAY.getValue() - localDate.getDayOfWeek().getValue()));
        LocalDate lastDay = inputDay.with(LAST_OF_WEEK);
        String[] weekInfo = {firstDay.toString(), lastDay.toString()};
        return weekInfo;
    }
    /**
     * 计算日期的年--天
     *
     * @param newDate
     * @return
     */
    public static int getDateYear(Date newDate) {

        Calendar newCal = Calendar.getInstance();
        newCal.setTime(newDate);
        return newCal.get(Calendar.YEAR);
    }

    /**
     * 获取当前日期所在月的开始日期和结束日期
     *
     * @param date
     * @return [0]-开始日期、[1]-结束日期
     */
    public static String[] getMonthInfo(String date) {
        LocalDate inputDay = LocalDate.parse(date);
        //一月的第一天
        LocalDate firstDay = LocalDate.of(inputDay.getYear(), inputDay.getMonth(), 1);
        //一月的最后一天
        LocalDate lastDay = inputDay.with(TemporalAdjusters.lastDayOfMonth());
        String[] monthInfo = {firstDay.toString(), lastDay.toString()};
        return monthInfo;
    }

    /**
     * 获取当前日期所在月的开始日期和结束日期
     *
     * @param date
     * @return [0]-开始日期、[1]-结束日期
     */
    public static String[] getDayInfo(String date) {
        LocalDate inputDay = LocalDate.parse(date);
        LocalDateTime today_start = LocalDateTime.of(inputDay, LocalTime.MIN);//当天零点
        LocalDateTime today_end = LocalDateTime.of(inputDay, LocalTime.MAX);//当天零点
        String[] dayInfo = {today_start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), today_end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))};
        return dayInfo;
    }

    public static Long Date2long(Date date){
        long time = date.getTime();
        return time;
    }

    /**
     * 获取一段时间的天数
     * @param currentDate
     * @param targetDate
     * @return
     */
    public static int days(Date currentDate, Date targetDate){
        String currentDateStr = format(currentDate, yyyy_MM_dd);
        String targetDateStr = format(targetDate, yyyy_MM_dd);
        long time = parse(currentDateStr+ " 00:00:00").getTime();
        long time1 = parse(targetDateStr+ " 00:00:00").getTime();
        return Integer.parseInt(((time-time1)/(1000L*60*60*24))+"");
    }

    /**
     * 获取当前时间点离一天结束剩余秒数
     * @param currentDate
     * @return
     */
    public static long getRemainSecondsOneDay(Date currentDate) {
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        return ChronoUnit.SECONDS.between(currentDateTime, midnight);
    }


    /**
     * 计算年龄
     * @param birthDay
     * @return
     * @throws Exception
     */
    public static Integer calcAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException("出生日期不能晚于当前日期");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            }else{
                age--;
            }
        }
        age = age == 0 ? 1 : age;
        return age;
    }

    /**
     * 计算是轮回的第几天
     * @param startDay 开始时间
     * @param endDay 结束时间
     * @param roudTime 几天一个轮回
     * @return
     */
    public static int calcRound(String startDay,String endDay,int roudTime){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Long time = null;
        try {
            time = org.apache.commons.lang3.time.DateUtils.addDays(sf.parse(endDay),1).getTime() - sf.parse(startDay).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long  residue = time % (roudTime * 24 * 60 * 60 * 1000);
        Long res = residue / (24 * 60 * 60 * 1000);
        if(res.intValue() == 0){
           return  roudTime;
        }
        return res.intValue();
    }

    public static String getDateStr(Date date, String format) {
        assert date != null && format != null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static void main(String[] args) {
        boolean before = isAfter(DateUtils.format(new Date()),getNowDateStrStart());
//        long l = System.currentTimeMillis();
//        Timestamp timestamp = new Timestamp(l);
//        Date date = new Date(l);
//        System.out.println(timestamp);
        System.out.println(before);
    }


}
