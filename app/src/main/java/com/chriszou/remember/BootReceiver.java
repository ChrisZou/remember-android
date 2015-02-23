/**
 * BootReceiver.java
 *
 * Created by zouyong on Jul 7, 2014,2014
 */
package com.chriszou.remember;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.chriszou.androidlibs.L;
import com.chriszou.remember.util.ReminderAlarmHelper;

/**
 * @author zouyong
 *
 */
public class BootReceiver extends BroadcastReceiver {

	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
        //Setup all alarms
        L.l("setup alarms");
        ReminderAlarmHelper.setupAlarms(context);
	}

}
