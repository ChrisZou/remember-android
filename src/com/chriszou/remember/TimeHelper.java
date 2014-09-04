/**
 * TimeHelper.java
 * 
 * Created by zouyong on Aug 20, 2014,2014
 */
package com.chriszou.remember;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;

/**
 * @author zouyong
 *
 */
public class TimeHelper {
    public static final long DAY_DURATION_MILLIS = 24*60*60*1000;
    
    /**
     * Get the next time in millisecond with the specified hour of day and minute, with second and millis set to 0
     * @param hour
     * @param minute
     * @return
     */
    public static long getNextHourAndMinite(int hour, int minute) {
    	Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long time = calendar.getTimeInMillis();
        while(time<System.currentTimeMillis()) {
        	time+= DAY_DURATION_MILLIS;
        }
        
        return time;
    }

	@SuppressLint("SimpleDateFormat")
	public static String getTodayString() {
		SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return sdfDateFormat.format(new Date());
	}
}
