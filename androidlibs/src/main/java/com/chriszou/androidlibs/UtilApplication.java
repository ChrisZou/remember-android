/**
 * MyApplication.java
 * 
 * Created by zouyong on Jun 4, 2014,2014
 */
package com.chriszou.androidlibs;

import android.app.Application;
import android.content.Context;

/**
 * @author zouyong
 *
 */
public class UtilApplication extends Application {
	private static Context sContext;
	@Override
	public void onCreate() {
		super.onCreate();

		initUtils(this);
		sContext = getApplicationContext();
	}

	public static Context getContext() {
		return sContext;
	}

	/**
	 * Initialize some utils classes that need Context
	 * @param context
	 */
	private void initUtils(Context context){
		Prefs.init(context);
		Res.init(context);
	}

}
