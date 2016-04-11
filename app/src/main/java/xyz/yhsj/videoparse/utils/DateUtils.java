package xyz.yhsj.videoparse.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtils {

    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss
     **/
    public static final String DF_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式：yyyy-MM-dd hh:mm:ss
     **/
    public static final String DF_YYYY_MM_DD_h_MM_SS = "yyyy-MM-dd hh:mm:ss";

    /**
     * 日期格式：yyyy-MM-dd HH:mm
     **/
    public static final String DF_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    /**
     * 日期格式：yyyy-MM-dd
     **/
    public static final String DF_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 日期格式：HH:mm:ss
     **/
    public static final String DF_HH_MM_SS = "HH:mm:ss";

    /**
     * 日期格式：HH:mm
     **/
    public static final String DF_HH_MM = "HH:mm";

    /**
     * 将日期格式化成友好的字符串：几分钟前、几小时前、几天前、几月前、几年前、刚刚
     *
     * @param date
     * @return
     */
    public static String formatFriendly(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        int nowYear = now.get(Calendar.YEAR);
        int nowMonth = now.get(Calendar.MONTH);
        int nowWeek = now.get(Calendar.WEEK_OF_MONTH);
        int nowDay = now.get(Calendar.DAY_OF_WEEK);
        int nowHour = now.get(Calendar.HOUR_OF_DAY);
        int nowMinute = now.get(Calendar.MINUTE);

        Calendar ca = Calendar.getInstance();
        if (date != null)
            ca.setTime(date);
        else
            ca.setTime(new Date());
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH);
        int week = ca.get(Calendar.WEEK_OF_MONTH);
        int day = ca.get(Calendar.DAY_OF_WEEK);
        int hour = ca.get(Calendar.HOUR_OF_DAY);
        int minute = ca.get(Calendar.MINUTE);
        if (year != nowYear) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //不同年份
            return sdf.format(date);
        } else {
            if (month != nowMonth) {
                //不同月份
                SimpleDateFormat sdf = new SimpleDateFormat("M月dd日");
                return sdf.format(date);
            } else {
                if (week != nowWeek) {
                    //不同周
                    SimpleDateFormat sdf = new SimpleDateFormat("M月dd日");
                    return sdf.format(date);
                } else if (day != nowDay) {
                    if (day + 1 == nowDay) {
                        return "昨天" + format(date, "HH:mm");
                    }
                    if (day + 2 == nowDay) {
                        return "前天" + format(date, "HH:mm");
                    }
                    //不同天
                    SimpleDateFormat sdf = new SimpleDateFormat("M月dd日");
                    return sdf.format(date);
                } else {
                    //同一天
                    int hourGap = nowHour - hour;
                    if (hourGap == 0)//1小时内
                    {
                        if (nowMinute - minute < 1) {
                            return "刚刚";
                        } else {
                            return (nowMinute - minute) + "分钟前";
                        }
                    } else if (hourGap >= 1 && hourGap <= 12) {
                        return hourGap + "小时前";
                    } else {
                        SimpleDateFormat sdf = new SimpleDateFormat("今天 HH:mm");
                        return sdf.format(date);
                    }
                }
            }
        }
    }

    /**
     * 获取当前时间戳
     *
     * @return 时间戳
     */
    public static long getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }

    /**
     * 格式化12小时制 格式：yyyy-MM-dd hh-mm-ss
     *
     * @param time 时间
     * @return
     */
    public static String format12Time(long time) {
        return format(time, DF_YYYY_MM_DD_h_MM_SS);
    }

    /**
     * 格式化24小时制 格式：yyyy-MM-dd HH-MM-ss
     *
     * @param time 时间
     * @return
     */
    public static String format24Time(long time) {
        return format(time, DF_YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 得到当前时间
     *
     * @return
     */
    public static Date getThisTime() {
        return new Date();
    }

    /**
     * 获取系统当前日期
     *
     * @return
     */
    public static String getThisTime(String formater) {
        return format(getCurrentTime(), formater);
    }

    /**
     * 将日期字符串转成日期
     *
     * @param strDate 日期字符串
     * @param format  格式
     * @return
     */
    public static Date parseDate(String strDate, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date returnDate = null;
        try {
            returnDate = dateFormat.parse(strDate);
        } catch (ParseException e) {
            Log.e("Utils", "parseDate failed !");


        }
        return returnDate;

    }

    /**
     * 验证日期是否比当前日期早
     *
     * @param target1 比较时间1
     * @param target2 比较时间2
     * @return true 则代表target1比target2晚或等于target2，否则比target2早
     */
    public static boolean compareDate(Date target1, Date target2) {
        boolean flag = false;
        try {
            String target1DateTime = format(target1, DF_YYYY_MM_DD_HH_MM_SS);
            String target2DateTime = format(target2, DF_YYYY_MM_DD_HH_MM_SS);
            if (target1DateTime.compareTo(target2DateTime) <= 0) {
                flag = true;
            }
        } catch (Exception e1) {
            System.out.println("比较失败，原因：" + e1.getMessage());
        }
        return flag;
    }

    /**
     * 得到某个时间点 正数为当前时间之后,负数为当前时间之前
     *
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @param format
     * @return 自定义
     */
    public static String getTimePoint(int year, int month, int day, int hour,
                                      int minute, int second, String format) {
        Calendar c = Calendar.getInstance();

        c.add(Calendar.YEAR, year);
        c.add(Calendar.MONTH, month);
        c.add(Calendar.DAY_OF_MONTH, day);
        c.add(Calendar.HOUR_OF_DAY, hour);
        c.add(Calendar.MINUTE, minute);
        c.add(Calendar.SECOND, second);

        if (format == null) {
            return format(c.getTimeInMillis(), DF_YYYY_MM_DD_HH_MM_SS);
        }
        return format(c.getTimeInMillis(), format);
    }

    /**
     * 格式化时间,自定义格式
     *
     * @param time     时间
     * @param formater 格式化时间用的标签
     * @return
     */
    public static String format(long time, String formater) {
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        return sdf.format(new Date(time));
    }

    /**
     * 将日期以yyyy-MM-dd HH:mm:ss格式化
     *
     * @param date 日期
     * @return
     */
    public static String format(Date date, String formater) {
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        return sdf.format(date);
    }

    /**
     * 获取当前年
     *
     * @return 年
     */
    public static String getYear() {
        return format(getCurrentTime(), "yyyy");
    }

    /**
     * 获取当前月
     *
     * @return 月
     */
    public static String getMonth() {
        return format(getCurrentTime(), "MM");
    }

    /**
     * 获取当前日
     *
     * @return 日
     */
    public static String getDay() {
        return format(getCurrentTime(), "dd");
    }

    /**
     * 获取当前小时
     *
     * @return 小时
     */
    public static String getHour() {
        return format(getCurrentTime(), "HH");
    }

    /**
     * 获取当前小时
     *
     * @return 小时
     */
    public static String getMinute() {
        return format(getCurrentTime(), "mm");
    }

    /**
     * 获取某天是星期几
     *
     * @param date
     * @return
     */
    public static String getMonthDayWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);    //获取年
        int month = c.get(Calendar.MONTH) + 1;   //获取月份，0表示1月份
        int day = c.get(Calendar.DAY_OF_MONTH);    //获取当前天数
        int week = c.get(Calendar.DAY_OF_WEEK);

        String weekStr = null;

        switch (week) {

            case Calendar.SUNDAY:
                weekStr = "周日";
                break;

            case Calendar.MONDAY:
                weekStr = "周一";
                break;

            case Calendar.TUESDAY:
                weekStr = "周二";
                break;

            case Calendar.WEDNESDAY:
                weekStr = "周三";
                break;

            case Calendar.THURSDAY:
                weekStr = "周四";
                break;

            case Calendar.FRIDAY:
                weekStr = "周五";
                break;

            case Calendar.SATURDAY:
                weekStr = "周六";
                break;
        }

        return month + "月" + day + "日" + "(" + weekStr + ")";
    }


}
