/**
 * AlarmReceiver.java
 * 
 * Created by zouyong on Aug 19, 2014,2014
 */
package com.chriszou.androidlibs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author zouyong
 *
 */
public class AlarmReceiver extends BroadcastReceiver{
	public static final String EXTRA_STRING_RUNNER = "extra_string_runner";

	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		Logger.log(context, "onReceive");
		L.l("onreceive");
		String runnerClass = intent.getStringExtra(EXTRA_STRING_RUNNER);
		try {
			Class clz = Class.forName(runnerClass);
			AlarmRunner runner =(AlarmRunner) clz.newInstance();
			runner.run(context, intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
