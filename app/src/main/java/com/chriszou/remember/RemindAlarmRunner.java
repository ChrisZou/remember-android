/**
 * RemindAlarmRunner.java
 *
 * Created by zouyong on Aug 19, 2014,2014
 */
package com.chriszou.remember;

import android.content.Context;
import android.content.Intent;

import com.chriszou.androidlibs.AlarmHelper;
import com.chriszou.androidlibs.AlarmRunner;
import com.chriszou.remember.model.ContentMode;
import com.chriszou.remember.util.AlarmUtils;

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
		String reminderType = intent.getBundleExtra(AlarmHelper.EXTRA_EXTRA).getString(ReminderActivity.EXTRA_STRING_REMINDER_TYPE,
				ContentMode.SHUFFLE.name());

        Intent activityIntent = ReminderActivity.createIntent(context, reminderType);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(activityIntent);

		AlarmUtils.setupAlarms(context);
	}

}
