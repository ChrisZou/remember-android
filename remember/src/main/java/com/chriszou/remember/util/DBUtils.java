package com.chriszou.remember.util;

import com.chriszou.androidlibs.CalendarUtil;
import com.chriszou.androidlibs.TimeHelper;
import com.chriszou.remember.model.ContentMode;
import com.chriszou.remember.model.Reminder;

import java.util.Calendar;

/**
 * Created by Chris on 1/7/15.
 */
public class DBUtils {

    public static void initFirstInstall() {
        initReminders(7, 0, ContentMode.SHUFFLE);
        initReminders(12, 0, ContentMode.SHUFFLE);
        initReminders(21, 30, ContentMode.TODAY);
    }

    private static void initReminders(int hour, int minute, ContentMode mode) {
        long time = TimeHelper.getNextHourAndMinite(hour, minute);
        Reminder reminder = new Reminder(time, mode);
        reminder.save();
    }
}
