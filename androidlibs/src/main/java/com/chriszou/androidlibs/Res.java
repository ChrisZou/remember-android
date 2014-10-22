/**
 * Res.java
 * 
 * Created by zouyong on Jun 4, 2014,2014
 */
package com.chriszou.androidlibs;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

/**
 * @author zouyong
 * Helper class for Resources. 
 * To use it, call its init() method in customized {@link Application} class's onCreate()
 */
public class Res {
	private static Resources sInstance;
    
	/**
     * This method should called in customized {@link Application} class's onCreate() 
	 * @param context
	 */
	static void init(Context context){
		sInstance = context.getResources();
	}
    
	public static Resources get() {
		return sInstance;
	}
}
