package com.global.common.utils.constants.tools;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-03-11 下午 05:35
 * @Version: v1.0
 */
@Slf4j
public class DateUtil {

    /**
     * 转换格式: 年-月-日
     */
    private static final String FORMAT_DATE = "yyyy-MM-dd";
    /**
     * 转换格式: 年-月-日 时:分:秒
     */
    private static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    /**
     * 转换格式: 年-月-日 时:分:秒.毫秒
     */
    private static final String FORMAT_DATE_TIME_MILLISECOND = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 创建时间转化模板对象
     * @param format 转换格式
     */
    private static SimpleDateFormat createSimpleDateFormat(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * 获取当前时间
     */
    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }


    /**
     * 时间转换
     * @param date 日期
     * @param format 转换格式
     */
    public static String formatting(Date date, String format) {
        return createSimpleDateFormat(format).format(date);
    }

    /**
     * 时间转换
     * @param dateStr 日期字符串
     * @param format 转换格式
     */
    public static Date formatting(String dateStr, String format) {
        try {
            return createSimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取计算后的日期
     * @param datetime 指定日期
     * @param value 计算值
     * @param type 计算类型
     */
    public static Date getCalculateDate(Date datetime, int value, int type) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datetime);
        cal.add(type, value);
        return cal.getTime();
    }

    /**
     * 获取当前的周期
     */
    public static int getCurrentDayOfWeek() {
        return getDayOfWeek(null);
    }

    /**
     * 获取指定时间的周期
     *
     * @param date 当前日期
     */
    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null)
            cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK);
        return week == 1 ? 7 : week - 1;
    }

    /**
     * 获取当前月份的第一天
     */
    public static Date currentMonthFirstDay() {
        return monthFirstDay(null);
    }

    /**
     * 获取某个月份的第一天
     * @param date 指定日期
     */
    public static Date monthFirstDay(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null)
            cal.setTime(date);
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 获取当前月份的最后一天
     */
    public static Date currentMonthLastDay() {
        return monthLastDay(null);
    }

    /**
     * 获取某个月份的最后一天
     * @param date 指定日期
     */
    public static Date monthLastDay(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null)
            cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }
}
