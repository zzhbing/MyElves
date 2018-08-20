package com.hangzhou.zhb.myelves.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zhb on 2018/3/22.
 */

public class MyTime {
	/**
	 * @param mDate				时间
	 * @param yyyyMMddHHmmss	时间格式
	 * @return date转String
	 */
	public static String DateToString(Date mDate,String yyyyMMddHHmmss) {
		try{
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					yyyyMMddHHmmss, Locale.CHINA);
			return sDateFormat.format(mDate);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param string			时间
	 * @param yyyyMMddHHmmss	时间格式
	 * @return String转date
	 */
	public static Date StringToDate(String string,String yyyyMMddHHmmss) {
		try{
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					yyyyMMddHHmmss, Locale.CHINA);
			return sDateFormat.parse(string);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param yyyyMMddHHmmss 时间格式
	 * @return 获取当前时间
	 */
	public static String getNewTime(String yyyyMMddHHmmss){
		try{
			return DateToString(new Date(),yyyyMMddHHmmss);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param date 指定日期
	 * @return 获取指定日期的年
	 */
	public static int getYear(Date date){
		try{
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.YEAR);
		}catch (Exception e){
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 获取指定日期的月份
	 * @param date 指定日期
	 * @return 月份从0开始计算，即一月份 = 0
	 */
	public static int getMonth(Date date){
		try{
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.MONTH) + 1;
		}catch (Exception e){
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 获取指定日期的日期
	 * @param date 指定日期
	 * @return 指定日期当月的第几天
	 */
	public static int getDayOfMonth(Date date){
		try{
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.DAY_OF_MONTH);
		}catch (Exception e){
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 获取指定日期的日期
	 * @param date 指定日期
	 * @return 指定日期当年的第几天，-1 报错
	 */
	public static int getDayOfYear(Date date){
		try{
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.DAY_OF_YEAR);
		}catch (Exception e){
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 判断指定时间是否为上午
	 * @param date 指定日期
	 * @return 0 表示上午,1 表示下午，-1 报错
	 */
	public static int isAM(Date date){
		try{
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			int isAM;
			if(cal.get(Calendar.AM_PM) == Calendar.AM){
				isAM = 0;
			}else {
				isAM = 1;
			}
			return isAM;
		}catch (Exception e){
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * @param date 指定日期
	 * @return 获取指定日期的时（12时），-1 报错
	 */
	public static int getHour12(Date date){
		try{
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.HOUR);
		}catch (Exception e){
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * @param date 指定日期
	 * @return 获取指定日期的时（24时），-1 报错
	 */
	public static int getHour24(Date date){
		try{
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.HOUR_OF_DAY);
		}catch (Exception e){
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * @param date 指定日期
	 * @return 获取指定日期的分，-1 报错
	 */
	public static int getMinute(Date date){
		try{
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.MINUTE);
		}catch (Exception e){
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * @param date 指定日期
	 * @return 获取指定日期的秒，-1 报错
	 */
	public static int getSeconds(Date date){
		try{
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.SECOND);
		}catch (Exception e){
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * @param date 指定日期
	 * @return 获取指定日期的毫秒，-1 报错
	 */
	public static int getMillisecond(Date date){
		try{
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.MILLISECOND);
		}catch (Exception e){
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 第几周
	 * @param date 指定时间
	 * @return 指定日期当月的第几周，-1 报错
	 */
	public static int getWeekOfMonth(Date date){
		try{
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.WEEK_OF_MONTH);
		}catch (Exception e){
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 第几周
	 * @param date 指定时间
	 * @return 指定日期当年的第几周，-1 报错
	 */
	public static int getWeekOfYear(Date date){
		try{
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.WEEK_OF_YEAR);
		}catch (Exception e){
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 获取指定日期的星期
	 * @param date 指定日期
	 * @return 1-7分别是周日至周一，-1 报错
	 */
	public static int getDayOfWeek(Date date){
		try{
			Calendar cal=Calendar.getInstance();
			cal.setTime(date);
			int week = -1;
			switch (cal.get(Calendar.DAY_OF_WEEK)){
				case Calendar.SUNDAY:
					week = 1;
					MyLog.i("今天是周日");
					break;
				case Calendar.MONDAY:
					week = 2;
					MyLog.i("今天是周一");
					break;
				case Calendar.TUESDAY:
					week = 3;
					MyLog.i("今天是周二");
					break;
				case Calendar.WEDNESDAY:
					week = 4;
					MyLog.i("今天是周三");
					break;
				case Calendar.THURSDAY:
					week = 5;
					MyLog.i("今天是周四");
					break;
				case Calendar.FRIDAY:
					week = 6;
					MyLog.i("今天是周五");
					break;
				case Calendar.SATURDAY:
					week = 7;
					MyLog.i("今天是周六");
					break;
				default:
					MyLog.i("今昔不只是何年月！··");
					break;
			}
			return week;
		}catch (Exception e){
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 比较两个时间先后关系
	 * @param date1 比较时间
	 * @param date2 比较时间
	 * @return 去年在今年之前，0 两个时间相同；1 date1较date2之后；-1 date1较date2之前;-2 报错
	 */
	public static int dayCompareTo(Date date1,Date date2){
		try{
			Calendar c1 = Calendar.getInstance();
			Calendar c2 = Calendar.getInstance();
			c1.setTime(date1);
			c2.setTime(date2);
			return c1.compareTo(c2);
		}catch (Exception e){
			e.printStackTrace();
			return -2;
		}
	}

	/**
	 * @param date 指定日期
	 * @param addSeconds 24 * 60 * 60 * 1000
	 * @return date之后多少秒（24小时内）
	 */
	public static String getNowDateForAdd(Date date, long addSeconds, String yyyyMMddHHmmss){
		try{
			if(addSeconds <= 24 * 60 * 60 * 1000){
				SimpleDateFormat df=new SimpleDateFormat(yyyyMMddHHmmss, Locale.CHINA);
				return df.format(new Date(date.getTime() + addSeconds));
			}else {
				return "24小时内可用！！";
			}
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param date 指定时间
	 * @param addDay 正数表示之后，负数表示之前
	 * @param yyyyMMddHHmmss 时间格式
	 * @return date之前多少天
	 */
	public static String getNowDateForAddDay(Date date,int addDay,String yyyyMMddHHmmss){
		try{
			SimpleDateFormat dft = new SimpleDateFormat(yyyyMMddHHmmss,Locale.CHINA);
			Calendar mCalendar = Calendar.getInstance();
			mCalendar.setTime(date);
			mCalendar.set(Calendar.DATE, mCalendar.get(Calendar.DATE) + addDay);
			Date endDate = null;
			endDate = dft.parse(dft.format(mCalendar.getTime()));
			return dft.format(endDate);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param date 指定时间
	 * @param addMouth 正数表示之后，负数表示之前
	 * @param yyyyMMddHHmmss 时间格式
	 * @return date之前多少月
	 */
	public static String getNowDateForAddMouth(Date date,int addMouth,String yyyyMMddHHmmss){
		try{
			SimpleDateFormat dft = new SimpleDateFormat(yyyyMMddHHmmss,Locale.CHINA);
			Calendar mCalendar = Calendar.getInstance();
			mCalendar.setTime(date);
			mCalendar.add(Calendar.MONTH , addMouth);
			return dft.format(date.getTime());
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param date 指定时间
	 * @param addYear 正数表示之后，负数表示之前
	 * @param yyyyMMddHHmmss 时间格式
	 * @return date之前多少年
	 */
	public static String getNowDateForAddYear(Date date,int addYear,String yyyyMMddHHmmss){
		try{
			SimpleDateFormat dft = new SimpleDateFormat(yyyyMMddHHmmss,Locale.CHINA);
			Calendar mCalendar = Calendar.getInstance();
			mCalendar.setTime(date);
			mCalendar.add(Calendar.YEAR , addYear);
			return dft.format(date.getTime());
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
