package com.chriszou.androidlibs;

import android.content.Context;
import android.widget.Toast;

public class Toaster {
	public static void s(Context context, int textResId) {
		Toast.makeText(context, textResId, Toast.LENGTH_SHORT).show();
	}

	public static void s(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
}
