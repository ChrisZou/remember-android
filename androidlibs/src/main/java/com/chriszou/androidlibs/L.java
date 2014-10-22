package com.chriszou.androidlibs;

import android.util.Log;


public class L {
	public static String TAG = "zyzy";
	public static boolean debug = true;

	private static final String CLASS_METHOD_LINE_FORMAT = "%s.%s() Line:%d---------%s";
	public static void l(String msg) {
		if(debug) {
			StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
			String className = traceElement.getClassName();
			String simpleClassName = className.contains(".")?className.substring(className.lastIndexOf(".")+1):className;

			String logText = String.format(CLASS_METHOD_LINE_FORMAT, simpleClassName, traceElement.getMethodName(),
					traceElement.getLineNumber(), msg.toString());

			Log.d(TAG, logText);
		}
	}

	public static void e(String msg) {
		if (debug) {
			StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
			String className = traceElement.getClassName();
			String simpleClassName = className.contains(".") ? className.substring(className.lastIndexOf(".") + 1) : className;

			String logText = String.format(CLASS_METHOD_LINE_FORMAT, simpleClassName, traceElement.getMethodName(),
					traceElement.getLineNumber(), msg.toString());

			Log.e(TAG, logText);
		}
	}

	public static void trace() {
		StackTraceElement[] traceElements = Thread.currentThread().getStackTrace();
		Log.d("zyzy", "trace-------------------------------------------------------------------------");
		int len = traceElements.length;
		for (int i=len-1; i>=0; i--) {
			StackTraceElement traceElement = traceElements[i];
			String logText = String.format(CLASS_METHOD_LINE_FORMAT, traceElement.getClassName(), traceElement.getMethodName(),
					traceElement.getLineNumber(), traceElement.getFileName());
			Log.d("zyzy", logText);// 打印Log
		}
		Log.d("zyzy", "end trace-------------------------------------------------------------------------");
	}

	/**
	 * Function: Throws an exception containing the detailed message. Should be used ONLY IN DEVELOPMENT AND TESTING.
	 * 
	 */
	public static void error(String msg) {
		if(debug) {
			throw new RuntimeException(msg);
		}
	}
}
