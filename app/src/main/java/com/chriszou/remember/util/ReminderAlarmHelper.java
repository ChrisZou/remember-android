/**
 * AlarmUtils.java
 *
 * Created by zouyong on Aug 20, 2014,2014
 */
package com.chriszou.remember.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.chriszou.androidlibs.AlarmHelper;
import com.chriszou.androidlibs.CalendarUtil;
import com.chriszou.androidlibs.TimeHelper;
import com.chriszou.remember.RemindAlarmRunner;
import com.chriszou.remember.ReminderActivity;
import com.chriszou.remember.model.ContentMode;
import com.chriszou.remember.model.Reminder;

import java.util.List;

/**
 * @author zouyong
 *
 */
public class ReminderAlarmHelper {
	public static void setupAlarms(Context context) {
        try {
            List<Reminder> reminders = Reminder.all();
            for (Reminder reminder : reminders) {
                setupAlarm(context, reminder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public static void setupAlarm(Context context, Reminder reminder) {
        while(reminder.time<System.currentTimeMillis()) {
            reminder.time += CalendarUtil.DAY_DURATION_MILLIS;
        }

        Bundle extra = new Bundle();
        extra.putString(ReminderActivity.EXTRA_STRING_REMINDER_TYPE, reminder.contentMode);

		AlarmHelper helper = new AlarmHelper(context);
		helper.setAlarm(reminder.getId().intValue(), reminder.time, RemindAlarmRunner.class.getName(), extra);
	}

    public static void cancelAlarm(Context context, Reminder reminder) {
        Bundle extra = new Bundle();
        extra.putString(ReminderActivity.EXTRA_STRING_REMINDER_TYPE, reminder.contentMode);

        AlarmHelper helper = new AlarmHelper(context);
        helper.cancelAlarm(reminder.getId().intValue(), RemindAlarmRunner.class.getName(), extra);
    }


    public static void setupTestingAlarm(Context context) {
//		setupAlarm(context, Integer.MAX_VALUE, System.currentTimeMillis(), ContentMode.SHUFFLE.name());
	}

}