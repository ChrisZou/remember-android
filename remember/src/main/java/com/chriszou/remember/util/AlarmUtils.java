/**
 * AlarmUtils.java
 *
 * Created by zouyong on Aug 20, 2014,2014
 */
package com.chriszou.remember.util;

import android.content.Context;
import android.os.Bundle;

import com.chriszou.androidlibs.AlarmHelper;
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
public class AlarmUtils {
	public static void setupAlarms(Context context) {
        try {
            List<Reminder> reminders = Reminder.all();
            for (Reminder reminder : reminders) {
                long id = reminder.getId();
                setupAlarm(context, (int)id, reminder.time, reminder.contentMode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	private static void setupAlarm(Context context, int id, long time, String type) {

        while(time<System.currentTimeMillis()) {
            time += TimeHelper.DAY_DURATION_MILLIS;
        }

        Bundle extra = new Bundle();
        extra.putString(ReminderActivity.EXTRA_STRING_REMINDER_TYPE, type);

		AlarmHelper helper = new AlarmHelper(context);
		helper.setAlarm(id, time, RemindAlarmRunner.class.getName(), extra);
	}

	public static void setupTestingAlarm(Context context) {
		setupAlarm(context, Integer.MAX_VALUE, System.currentTimeMillis(), ContentMode.SHUFFLE.name());
	}
}