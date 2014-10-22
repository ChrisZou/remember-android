/**
 * CalendarUtil.java
 * 
 * Created by zouyong on 11:45:50 AM, 2014
 */
package com.chriszou.androidlibs;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;

/**
 * A utility of the java.util.Calendar class
 * @author zouyong
 *
 */
@SuppressLint("SimpleDateFormat")
public class CalendarUtil {

	public static final long DAY_DURATION_MILLIS = 24 * 60 * 60 * 1000;
	/**
	 * Get the current hour in 24-hour format
	 * @return
	 */
	public static int getCurrentHour() {
		return getHour24(System.currentTimeMillis());
	}

	/**
	 * Get the hour field of a given time in millisecond
	 * @param time
	 * @return
	 */
	public static int getHour24(long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * Get the current minute in an hour
	 * @return
	 */
	public static int getCurrentMinute() {
		return getMinute(System.currentTimeMillis());
	}

	/**
	 * Get the minute field of a given time millisecond
	 * @param time
	 * @return
	 */
	public static int getMinute(long time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		return c.get(Calendar.MINUTE);
	}

	/**
	 * Get the current date-time string in format "YYYY-MM-DD HH:mm:ss"
	 * @return
	 */
	public static String getDateTimeString() {
		return getDateTimeString(System.currentTimeMillis());
	}

	/**
	 * Get the date-time string for the specific time in format "YYYY-MM-DD HH:mm:ss"
	 * @param time
	 * @return
	 */
	public static String getDateTimeString(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(time));
	}

	public static boolean isToday(long time) {
		String todayString = TimeHelper.getTodayString();
		String timeString = TimeHelper.getTimeFormat("yyyy-MM-dd", time);
		return todayString.equals(timeString);
	}

	/**
	 * Get the starting millisecond of today
	 * @return
	 */
	public static long getTodayStart() {
		long now = System.currentTimeMillis();
		if(now%DAY_DURATION_MILLIS==0) {
			now +=5;
		}

		long remainder = now % DAY_DURATION_MILLIS;
		return now - remainder;
	}

	/**
	 * Get the ending millisecond of today
	 * @return
	 */
	public static long getTodayEnd() {
		return getTodayStart() + DAY_DURATION_MILLIS;
	}


}
