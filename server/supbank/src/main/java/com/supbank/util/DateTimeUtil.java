package com.supbank.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeUtil {
	/**
	 * 获取现在时间
	 * 
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static String getNowDateStr() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(currentTime);
	}

	/**
	 * 当前天字符串
	 */
	public static String getNowDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(currentTime);
	}

	/**
	 * 当前天字符串
	 */
	public static String getNowDayStr() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		return formatter.format(currentTime);
	}

	/**
	 * 当天时分秒字符串
	 */
	public static String getNowTimeStr() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(currentTime);
	}

	/**
	 * 当月第一天
	 */
	public static String getThisMonthFirstDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-01");
		// 获取当前月第一天：
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		// 设置为1号,当前日期既为本月第一天
		c.set(Calendar.DAY_OF_MONTH, 1);
		return formatter.format(c.getTime());
	}

	/**
	 * 当月最后一天
	 */
	public static String getThisMonthLastDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH,
				ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return formatter.format(ca.getTime());
	}

	/**
	 * 上月第一天
	 */
	public static String getPreMonthFirstDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal_1 = Calendar.getInstance();
		cal_1.add(Calendar.MONTH, -1);
		// 设置为1号,当前日期既为本月第一天
		cal_1.set(Calendar.DAY_OF_MONTH, 1);
		return format.format(cal_1.getTime());
	}

	/**
	 * 上月最后一天
	 */
	public static String getPreMonthLastDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();
		cale.set(Calendar.DAY_OF_MONTH, 0);
		return format.format(cale.getTime());
	}

	/**
	 * 当前月前N月月份字符串
	 * 
	 * @param zone
	 * @throws ParseException
	 */
	public static String getPreMonthStr(int monthNum) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		Calendar cal_1 = Calendar.getInstance();
		cal_1.add(Calendar.MONTH, -monthNum);
		// 设置为1号,当前日期既为本月第一天
		cal_1.set(Calendar.DAY_OF_MONTH, 1);
		return format.format(cal_1.getTime());
	}

	/**
	 * 某月前一月第一天
	 * 
	 * @param zone
	 * @throws ParseException
	 */
	public static String getPreMonthFirstDate(String monthstr)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = format1.parse(monthstr, pos);
		Calendar cal_1 = Calendar.getInstance();
		cal_1.setTime(strtodate);
		cal_1.add(Calendar.MONTH, -1);
		// 设置为1号,当前日期既为本月第一天
		cal_1.set(Calendar.DAY_OF_MONTH, 1);
		return format.format(cal_1.getTime());
	}

	/**
	 * 某月前一月最后一天
	 */
	public static String getPreMonthLastDate(String monthstr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = format1.parse(monthstr, pos);
		Calendar cale = Calendar.getInstance();
		cale.setTime(strtodate);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		return format.format(cale.getTime());
	}

	/**
	 * 某月第一天
	 * 
	 * @param zone
	 * @throws ParseException
	 */
	public static String getMonthFirstDate(String monthstr)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = format1.parse(monthstr, pos);
		Calendar cal_1 = Calendar.getInstance();
		cal_1.setTime(strtodate);
		// 设置为1号,当前日期既为本月第一天
		cal_1.set(Calendar.DAY_OF_MONTH, 1);
		return format.format(cal_1.getTime());
	}

	/**
	 * 某月最后一天
	 */
	public static String getMonthLastDate(String monthstr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = format1.parse(monthstr, pos);
		Calendar cale = Calendar.getInstance();
		cale.setTime(strtodate);
		cale.set(Calendar.DAY_OF_MONTH,
				cale.getActualMaximum(Calendar.DAY_OF_MONTH));
		return format.format(cale.getTime());
	}

	/**
	 * 某月前N月第一天
	 * 
	 * @param zone
	 * @throws ParseException
	 */
	public static String getMonthFirstDateByMonthNum(String monthstr,
			int monthNum) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = format1.parse(monthstr, pos);
		Calendar cal_1 = Calendar.getInstance();
		cal_1.setTime(strtodate);
		cal_1.add(Calendar.MONTH, -monthNum);
		// 设置为1号,当前日期既为本月第一天
		cal_1.set(Calendar.DAY_OF_MONTH, 1);
		return format.format(cal_1.getTime());
	}

	/**
	 * 某月前N月最后一天
	 */
	public static String getMonthLastDateByMonthNum(String monthstr,
			int monthNum) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = format1.parse(monthstr, pos);
		Calendar cale = Calendar.getInstance();
		cale.setTime(strtodate);
		cale.add(Calendar.MONTH, -monthNum + 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		return format.format(cale.getTime());
	}

	/**
	 * 获取对应日期是周几
	 */
	public static int getWeekDayDateStr(String datetime) {

		Calendar calendar = getCalendarByDateStr(datetime);
		int weekNum = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (weekNum == 0) {
			weekNum = 7;
		}
		return weekNum;
	}

	/**
	 * 根据日期字符串获取Calendar对象
	 */
	public static Calendar getCalendarByDateStr(String datetime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = new java.util.Date();
		try {
			date = sdf.parse(datetime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * 获取中文周几
	 */
	public static String getWeekNumCN(int weekNum) {
		String result = "";
		switch (weekNum) {
		case 1:
			result = "一";
			break;
		case 2:
			result = "二";
			break;
		case 3:
			result = "三";
			break;
		case 4:
			result = "四";
			break;
		case 5:
			result = "五";
			break;
		case 6:
			result = "六";
			break;
		case 7:
			result = "日";
			break;
		default:
			break;

		}
		return result;
	}

	/**
	 * Calendar对象转换成String yyyy-MM-dd
	 */
	public static String calendarToString(Calendar c) {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = df.format(c.getTime());
		return dateStr;
	}

	/**
	 * Calendar对象转换成String
	 */
	public static String calendarToStringByFormat(Calendar c, String format) {

		SimpleDateFormat df = new SimpleDateFormat(format);
		String dateStr = df.format(c.getTime());
		return dateStr;
	}

	/**
	 * 获取两个日期相差多少天
	 */
	public static long getMinusDay(String fromDate, String endDate) {
		long day = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {

			java.util.Date now = df.parse(fromDate);

			java.util.Date date = df.parse(endDate);

			long l = date.getTime() - now.getTime();

			day = l / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return day;
	}

	/**
	 * 日期加相应天数
	 * 
	 * @throws ParseException
	 */
	public static String addDaysByDateStr(String date, int day) {
		Calendar calendar = getCalendarByDateStr(date);
		calendar.add(Calendar.DATE, day);
		return calendarToString(calendar);
	}

	/**
	 * 日期加相应天数
	 * 
	 * @throws ParseException
	 */
	public static String addHoursByDateStr(String date, int hour) {
		Calendar calendar = getCalendarByDateStr(date);
		calendar.add(Calendar.HOUR, hour);
		return calendarToStringByFormat(calendar, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 根据格式获取当前时间
	 */
	public static String getNowDateStrByFormat(String format) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(currentTime);
	}

	/**
	 * 根据格式获取当前时间+-时间差
	 * 
	 * @param format
	 *            日期格式
	 * @param timeSpan
	 *            毫秒
	 * @return
	 */
	public static String getDateStrByFormatCompareNow(String format,
			int timeSpan) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(new Date(currentTime.getTime() + timeSpan));
	}

	/**
	 * 根据格式获取日期
	 */
	public static String getDateStrByFormat(String datetime, String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sdf.parse(datetime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	/**
	 * 获取英文日期格式
	 * 
	 * @param datetime
	 * @param format
	 * @return
	 */
	public static String getDateStrByFormatEN(String datetime, String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sdf.parse(datetime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format,
				Locale.ENGLISH);
		return formatter.format(date);
	}

	/**
	 * 获取带星期的日期
	 * 
	 * @return
	 */
	public static String getChinaNowDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 E",
				Locale.CHINA);
		return formatter.format(currentTime);
	}

	/**
	 * Date 转换 String By Format author gening date 2015年5月16日
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @throws Exception
	 */
	public static String date2String(Date date, String format) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	public static Date string2Date(String v, String format) throws Exception {
		DateFormat fm = new SimpleDateFormat(format);
		Date date = fm.parse(v);
		return date;
	}


	public static int compareDate(String nowtime, String plantime)
			throws Exception {

		Date now = string2Date(nowtime, "yyyy-MM-dd HH:mm:ss");
		Date plan = string2Date(plantime, "yyyy-MM-dd HH:mm:ss");
		return now.compareTo(plan);

	}

	public static int compareDay(String nowtime, String plantime)
			throws Exception {

		Date now = string2Date(nowtime, "yyyy-MM-dd");
		Date plan = string2Date(plantime, "yyyy-MM-dd");
		return now.compareTo(plan);

	}
	/**
	 * 获取最近一周，周列表
	 * @return
	 */
	public static List getCurrentWeekList() {
		ArrayList list = new ArrayList();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,-7);
		for(int i=0;i<7;i++)
		{
			calendar.add(Calendar.DATE,1);
			list.add(formatter.format(calendar.getTime()));
		}
		return list;
	}
}
