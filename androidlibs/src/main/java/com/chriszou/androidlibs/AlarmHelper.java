/**
 * AlarmHelper.java
 * 
 * Created by zouyong on Aug 19, 2014,2014
 */
package com.chriszou.androidlibs;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * A helper class to set/remove alarm
 * @author zouyong
 *
 */
public class AlarmHelper {

	private Context mContext;
	public static final String EXTRA_EXTRA = "extra_extra";

	public AlarmHelper(Context context){
		this.mContext = context;
	}

	public void setAlarm(Context context, int id, long time, String runnerName, Bundle extras) {
		Intent intent = new Intent(mContext, AlarmReceiver.class);
		intent.putExtra(EXTRA_EXTRA, extras);
		intent.putExtra(AlarmReceiver.EXTRA_STRING_RUNNER, runnerName);
		PendingIntent pi = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager aManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		aManager.set(AlarmManager.RTC, time, pi);

		Logger.log(context, "Set alarm on: "+CalendarUtil.getDateTimeString(time));
		L.l("set alarm on: "+CalendarUtil.getDateTimeString(time));
	}
}
