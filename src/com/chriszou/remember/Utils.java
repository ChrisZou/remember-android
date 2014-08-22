/**
 * Utils.java
 * 
 * Created by zouyong on Aug 20, 2014,2014
 */
package com.chriszou.remember;

import android.content.Context;
import android.os.Bundle;

import com.chriszou.androidlibs.AlarmHelper;
import com.chriszou.remember.ShufferActivity.ReminderType;

/**
 * @author zouyong
 *
 */
public class Utils {
    public static void setupAlarms(Context context) {
        
    	//First alarm
        setupAlarm(context, 1, 12, 30, ReminderType.SHUFFLE);
        setupAlarm(context, 2, 18, 30, ReminderType.SHUFFLE);
        setupAlarm(context, 3, 22, 30, ReminderType.TODAY);
    }
    
    private static void setupAlarm(Context context, int id, int hour, int minute, ReminderType type) {
    	AlarmHelper helper = new AlarmHelper(context);
    	
    	//First alarm
        Bundle extra = new Bundle();
        extra.putString(ShufferActivity_.EXTRA_STRING_REMINDER_TYPE, type.name());
        long time1 = TimeHelper.getNextHourAndMinite(hour, minute);
        helper.setAlarm(context, id, time1, RemindAlarmRunner.class.getName(), extra);
    }
}
