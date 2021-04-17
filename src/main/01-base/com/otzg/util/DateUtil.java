package com.otzg.util;

import com.otzg.log.util.LogUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

public class DateUtil {

	/**
	 * 字符串转日期
	 * @author G/2015-1-15 下午2:33:02
	 * @param day
	 * @return
	 */
	public static Date str2Date(String day,String pattern){
		Date date = new Date();
		DateFormat formatter = new SimpleDateFormat(pattern);
		try {
			date = formatter.parse(day);
		} catch (ParseException e) {
		}

		return date;
	}

	/**
	 * 字符串转日期
	 * @author G/2015-1-15 下午2:33:02
	 * @param day
	 * @return
	 */
	public static Date str2Date(String day){
		Date date = new Date();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = (Date)formatter.parse(day);
		} catch (ParseException e) {
		}

		return date;
	}

	/**
	 * 字符串转日期时间
	 * @author G/2015-1-16 上午8:14:57
	 * @param day
	 * @return
	 */
	public static Date str2DateTime(String day){
		Date date = new Date();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = (Date)formatter.parse(day);
		} catch (ParseException e) {
		}
		return date;
	}


	/**
	 * 日期转字符串
	 * @author G/2015-1-15 下午2:32:54
	 * @param d
	 * @return yyyy-MM-dd
	 */
	public static String date2Str(Date d,String pattern){
		return new SimpleDateFormat(pattern).format(d);
	}

	/**
	 * 日期转字符串
	 * @author G/2015-1-15 下午2:32:54
	 * @param d
	 * @return yyyy-MM-dd
	 */
	public static String date2Str(Date d){
		return new SimpleDateFormat("yyyy-MM-dd").format(d);
	}
	
	/**
	 * 日期转字符串
	 * @author G/2015-1-15 下午2:32:54
	 * @param d
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String dateTime2Str(Date d){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
	}
	
    /**
     * 获取当前日期时间
     */ 
	public static Date now(){
		return new Date();
	}

	/** 
	 * 时间戳转日期
	 * @author G/2018/6/14 12:19
	 * @param time       
	 */
	public static Date getDate(long time){
		return new Date(time);
	}

	/**
	 * 得到几天前的时间
	 * @author G/2015-1-16 上午8:15:56
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date dateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 得到几天后的时间
	 * @author G/2015-1-16 上午8:16:02
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date dateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	/**
	 * 得到几分钟后的时间
	 * @author G/2015-1-16 上午8:16:02
	 * @param d
	 * @param min 加的分钟数
	 * @return
	 */
	public static Date minutesAfter(Date d, int min) {
		Calendar nowTime = Calendar.getInstance();
		nowTime.setTime(d);
		nowTime.add(Calendar.MINUTE,min);
		return nowTime.getTime();
	}
	
	/**
	 * 得到当天的星期
	 * @author G/2015-1-16 上午8:16:10
	 * @return
	 */
	public final static int week() {
		Calendar now = Calendar.getInstance();
		return (now.get(Calendar.DAY_OF_WEEK) - 1);
	}
			
	/**
	 *  得到当前年份 2015
	 * @author G/2015-1-16 上午8:16:16
	 * @return 2015
	 */
	public final static int year(){
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return year;
	}

	/**
	 * 获取当前月整数形式
	 * @author:G/2017年10月31日
	 * @param
	 * @return
	 */
	public final static int month(){
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH )+1;
		return month;
	}

	/**
	 *  得到某年月的第一天
	 * @author G/2015-1-16 上午8:17:56
	 * @param year
	 * @param month
	 * @return
	 */
	public final static String getFirstDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
		return new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
	}

	/**
	 *  得到某年月的最后一天
	 * @author G/2015-1-16 上午8:25:01
	 * @param year
	 * @param month
	 * @return
	 */
	public final static String getLastDayOfMonth(int year,int month) {
		String monthStr=","+month;
		//这里的返回值要区分1,3,5,7,8,10,12
		if(",1,3,5,7,8,10,12".indexOf(monthStr)>=0){
			return year+"-"+month+"-"+31;
		}else if(",4,6,9,11".indexOf(monthStr)>=0){
			return year+"-"+month+"-"+30;
		}else{
			//判断闰年
			if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
				return year+"-"+month+"-"+29;
			}else{
				return year+"-"+month+"-"+28;
			}
		}
	}

	/**
	 *  得到当前年份和月份2012-05
	 * @author G/2015-1-16 上午8:16:24
	 * @return
	 */
	public final static String yearMonth() {
		final SimpleDateFormat dmonth = new SimpleDateFormat("yyyy-MM");
		final Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, 1); // first day of month
		return dmonth.format(c.getTime());
	}

	/**
	 *  得到当前年份和月份的第一天2012-05-01
	 * @author G/2015-1-16 上午8:16:29
	 * @return
	 */
	public final static String yearMonthDay1st() {
		final SimpleDateFormat dm = new SimpleDateFormat("yyyy-MM-dd");
		final Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, 1); // first day of month
		return dm.format(c.getTime());
	}

	/**
	 *  得到当前年份和月份天2012-05-01
	 * @author G/2015-1-16 上午8:16:33
	 * @return
	 */
	public final static String yearMonthDay() {
		final SimpleDateFormat dm = new SimpleDateFormat("yyyy-MM-dd");
		return dm.format(now());
	}

	/**
	 *  得到当前4位年份2位月份2位天20120501
	 * @author G/2015-1-16 上午8:16:33
	 * @return
	 */
	public final static String yyyymmdd() {
		final SimpleDateFormat dm = new SimpleDateFormat("yyyyMMdd");
		return dm.format(now());
	}
	
	/**
	 *  得到当前2位月份2位天0501
	 * @author G/2015-1-16 上午8:16:33
	 * @return
	 */
	public final static String mmdd() {
		final SimpleDateFormat dm = new SimpleDateFormat("MMdd");
		return dm.format(now());
	}
	
	/**
	 *  得到当前6位 时分秒
	 * @author G/2015-1-16 上午8:16:33
	 * @return
	 */
	public final static String hhmmss() {
		final SimpleDateFormat dm = new SimpleDateFormat("HHmmss");
		return dm.format(now());
	}
	
	/**
	 * 得到当前年、月、日 时、分、秒 2012-05-01 8:16:39
	 * @author G/2015-1-16 上午8:16:39
	 * @return "yyyy-MM-dd HH:mm:ss"
	 */
	public final static String yearMonthDayTime() {
		final SimpleDateFormat dm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dm.format(now());
	}
	
	/**
	 * 得到最简年月日时分秒表示
	 * @author G/2016年5月13日 下午3:46:32
	 * @return "yMdHms"
	 */
	public final static String yearMonthDayTimeShort() {
		final SimpleDateFormat dm = new SimpleDateFormat("yMdHms");
		return dm.format(now());
	}
	
	/**
	 * 得到最简年月日表示
	 * @author G/2016年5月13日 下午3:46:32
	 * @return "yMd"
	 */
	public final static String yearMonthDayShort(){
		final SimpleDateFormat dm = new SimpleDateFormat("yMd");
		return dm.format(now());
	}
	
	/**
	 * 得到当前时分秒
	 * @author G/2015-1-16 上午8:16:44
	 * @return "HH:mm:ss"
	 */
	public final static String time() {
		final SimpleDateFormat dm = new SimpleDateFormat("HH:mm:ss");
		return dm.format(now());
	}

	/**
	 * 比较两个日期大小
	 * @author G/2015-1-16 上午8:16:50
	 * @param date1
	 * @param date2
	 * @return
	 */
	public final static long compareDate(Date date1,Date date2){
		return date1.getTime()-date2.getTime();
	}

	/**
	 * 比较两个日期时间的大小
	 * @author G/2015-1-16 上午8:16:50
	 * @param time1
	 * @param time2
	 * @return
	 */
	public final static long compareTime(String time1,String time2){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long l = 0;
		try {
			Date now = df.parse(time1);
			Date date=df.parse(time2);
			l=now.getTime()-date.getTime();
		} catch (ParseException e) {}
		return l;
	}
	
	/**
	 * 比较一个日期时间和当前时间相差的毫秒数
	 * @author G/2016年8月8日 下午3:54:50
	 * @param dateTime
	 * @return
	 */
	public final static long compareTimeNow(Date dateTime){
		long l = 0;
		l=now().getTime()-dateTime.getTime();
		return l;
	}
	
	/**
	 *  得到当前年份和月份天2012-05-01
	 * @author G/2015-1-16 上午8:16:58
	 * @param day
	 * @return
	 */
	public final static String yearMonthDayTime(int day) {
		final Calendar now = Calendar.getInstance();
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now.getTime());
	}

	/**
	 *  得到某年月的第一天
	 * @author G/2015-1-16 上午8:17:56
	 * @param year
	 * @param month
	 * @return
	 */
	public final static String firstDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
		return new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
	}

	/**
	 *  得到某年月的最后一天
	 * @author G/2015-1-16 上午8:25:01
	 * @param year
	 * @param month
	 * @return
	 */
	public final static String lastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		return new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
	}

	/**
	 *  得到某年月的每一天
	 * @author G/2015-1-16 上午8:24:43
	 * @param year
	 * @param month
	 * @return
	 */
	public final static String[] daysOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		int end = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		String[] days = new String[end];
		for (int i = 0; i < end; i++) {
			cal.set(Calendar.DAY_OF_MONTH, i + 1);
			days[i] = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		}
		return days;
	}

	/**
	 * 某年的月份显示
	 * @author:G/2018年1月19日
	 * @param 
	 * @return
	 */
	public final static String[] monthsOfYear(int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		int end = cal.getActualMaximum(Calendar.MONTH)+1;
		String[] months = new String[end];
		for (int i = 0; i < end; i++) {
			cal.set(Calendar.MONTH,i);
			months[i] = new SimpleDateFormat("yyyy-MM").format(cal.getTime());
		}
		return months;
	}

	/**
	 * 计算两个日期之间相差的分钟数
	 * @author:G/2017年9月1日
	 * @param
	 * @return
	 */
	public final static long minutesBetween(Date date1,Date date2){
		return (date1.getTime()-date2.getTime())/(1000*60);
	}

	/**
	 * 计算两个日期之间相差的小时数
	 * @author:G/2017年9月23日
	 * @param
	 * @return
	 */
	public final static long hoursBetween(Date date1,Date date2){
		return (date1.getTime()-date2.getTime())/(1000*3600);
	}

	/** 
    * 计算两个日期之间相差的天数 
     */  
    public final static long daysBetween(Date date1,Date date2){  
		return (date1.getTime()-date2.getTime())/(1000*3600*24);
    }

	/**
	 * 计算两个日期之间月份差
	 */
	public final static int monthsBetween(Date date1,Date date2){
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		if(c1.getTimeInMillis() < c2.getTimeInMillis()) return 0;
		int year1 = c1.get(Calendar.YEAR);
		int year2 = c2.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH);
		int month2 = c2.get(Calendar.MONTH);
		int day1 = c1.get(Calendar.DAY_OF_MONTH);
		int day2 = c2.get(Calendar.DAY_OF_MONTH);
		// 获取年的差值 假设 date1 = 2015-8-16  date2 = 2011-9-30
		int yearInterval = year1 - year2;
		// 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
		if(month1 < month2 || month1 == month2 && day1 < day2) yearInterval --;
		// 获取月数差值
		int monthInterval =  (month1 + 12) - month2  ;
		if(day1 < day2) monthInterval --;
		monthInterval %= 12;
		return yearInterval * 12 + monthInterval;
	}

    /**
     * 分钟数转换成小时，超过1秒钟也算1小时
     * @author:G/2018年1月19日
     * @param 
     * @return
     */
    public final static long minutes2hours(long minutes) {
    	return (minutes%60>0?(minutes/60+1):(minutes/60));
    }
    
    public final static long hours2days(long hours) {
    	return (hours/24);
    }
    
    /**
     * 获取中文周
     * @author:G/2017年9月27日
     * @param 
     * @return
     */
    public final static String chnWeek(int w){
    	String r="";
    	switch(w){
	    	case 1:
	    		r = "一";
	    		break;
	    	case 2:
	    		r = "二";
	    		break;
	    	case 3:
	    		r = "三";
	    		break;
	    	case 4:
	    		r = "四";
	    		break;
	    	case 5:
	    		r = "五";
	    		break;
	    	case 6:
	    		r = "六";
	    		break;
	    	case 7:
	    		r = "日";
	    		break;
    	}
    	return r;
    }

	/**
	 * 返回一天的最后一秒
	 * @param endTime
	 * @return
	 */
	public final static String getEndTime(String endTime){
		String sj = " 23:59:59";
		//如果传的是日期型，则后面加到当然最后一秒
		if(Optional.ofNullable(endTime).isPresent()
				&& !endTime.contains(":")){
			return endTime+sj;
		}
		//如果是时间型则直接返回
		return endTime;
	}

	public static void main(String args[]) throws InterruptedException {
		String a = "2019-10-30";

		LogUtil.print(compareTimeNow(DateUtil.str2Date(a)));

	}
}
