/**
 * Logger.java
 * 
 * Created by zouyong on Sep 17, 2014,2014
 */
package com.chriszou.androidlibs;

import java.io.File;
import java.io.FileWriter;

import android.content.Context;
import android.os.Environment;

/**
 * Log app running statistics to file
 * @author zouyong
 *
 */
public class Logger {

	private static final String LOG_DIR = Environment.getExternalStorageDirectory().getAbsolutePath()+"/my_app_logs/";

	private static String getLogFilePath(Context context) {
		String appName = context.getString(R.string.app_name);
		while (appName.contains(" ")) {
			appName = appName.replace(" ", "_");
		}

		File f = new File(LOG_DIR);
		if(!f.exists()) {
			f.mkdirs();
		}

		return (LOG_DIR+"/"+appName+".log");
	}

	public static void log(Context context, String msg) {
		try {
			FileWriter fileWriter = new FileWriter(getLogFilePath(context), true);
			msg = "\n"+getTimeString()+": "+msg+"\n";
			fileWriter.write(msg);
			fileWriter.flush();
			fileWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getTimeString() {
		return CalendarUtil.getDateTimeString();
	}
}
