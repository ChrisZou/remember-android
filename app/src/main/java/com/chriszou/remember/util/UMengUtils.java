package com.chriszou.remember.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;

import com.chriszou.remember.BuildConfig;
import com.chriszou.remember.R;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

/**
 * Created by Chris on 4/2/15.
 */
public class UMengUtils {
    public static final String EVENT_﻿APP_START = "﻿app_start";
    public static final String EVENT_NOTES_LOADED = "notes_loaded";
    public static final String EVENT_ADD_CLICKED = "add_click";
    public static final String EVENT_SETTINGS_CLICKED = "settings_click";
    public static final String EVENT_PULL_TO_REFRESH = "pull_to_refresh";
    public static final String EVENT_NOTE_ITEM_CLICKED = "note_item_clicked";
    public static final String EVENT_REMINDERS_CLICKED = "reminders_clicked";
    public static final String EVENT_USER_INFO_CLICKED = "user_info_clicked";
    public static final String EVENT_LOGOUT_CLICKED = "logout_clicked";
    public static final String EVENT_REMINDER_ITEM_CLICKED = "reminder_item_clicked";
    public static final String EVENT_ADD_REMINDER_CLICKED = "add_reminder_clicked";
    public static final String EVENT_ADDED_REMINDER = "added_reminder";
    public static final String EVENT_MODIFIED_REMINDER = "modified_reminder";
    public static final String EVENT_REMOVED_REMINDER = "removed_reminder";
    public static final String EVENT_MODIFY_REMINDER_CLICKED = "modify_reminder_clicked";

    public static void logEvent(Context context, String eventId) {
        try {
            MobclickAgent.onEvent(context, eventId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void logEvent(Context context, String eventId, HashMap data) {
        try {
            MobclickAgent.onEvent(context, eventId, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void logEventValue(Context context, String eventId, HashMap data, int value) {
        try {
            MobclickAgent.onEventValue(context, eventId, data, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void logAppStartEvent(Activity activity) {
        HashMap data = new HashMap();
        try {
            data.put("android_os_version", VERSION.RELEASE);
            data.put("app_version_name", BuildConfig.VERSION_NAME);
            data.put("app_version_code", BuildConfig.VERSION_CODE+"");
            data.put("phone_model", Build.MANUFACTURER+"::"+Build.MODEL);
            data.put("distribution_channel", activity.getString(R.string.distribution_channel));
        } catch (Exception e) {
            e.printStackTrace();
        }
        logEvent(activity, EVENT_﻿APP_START, data);
    }
}
