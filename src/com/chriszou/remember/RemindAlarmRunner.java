/**
 * RemindAlarmRunner.java
 * 
 * Created by zouyong on Aug 19, 2014,2014
 */
package com.chriszou.remember;

import android.content.Context;
import android.content.Intent;

import com.chriszou.androidlibs.AlarmRunner;

/**
 * @author zouyong
 *
 */
public class RemindAlarmRunner implements AlarmRunner{

	/* (non-Javadoc)
	 * @see com.chriszou.androidlibs.AlarmRunner#run(android.content.Context, android.content.Intent)
	 */
	@Override
	public void run(Context context, Intent intent) {
        Intent activityIntent = new Intent(context, ShufferActivity_.class);
        String reminderType = intent.getStringExtra(ShufferActivity_.EXTRA_STRING_REMINDER_TYPE);
        activityIntent.putExtra(ShufferActivity_.EXTRA_STRING_REMINDER_TYPE, reminderType);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(activityIntent);
        
		Utils.setupAlarms(context);
	}

}
