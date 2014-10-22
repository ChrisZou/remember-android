/**
 * Notifier.java
 * 
 * Created by zouyong on Sep 8, 2014,2014
 */
package com.chriszou.androidlibs;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * A helper class to show a notification on the notificationbar;
 * @author zouyong
 *
 */
public class Notifier {

    private boolean mOnGoing;
    public Context mContext;
    public Notifier(Context context) {
    	mContext = context;
    }
    
    public void setOnGoing(boolean ongoing) {
    	mOnGoing = ongoing;
    }
    
    @SuppressLint("NewApi")
	public void fireActivity(int icon, String title, String text, Class targetActivity) {
    	Intent resultIntent = new Intent();
        resultIntent.setClass(mContext, targetActivity);
        resultIntent.setAction(Intent.ACTION_MAIN);
        resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent pi = PendingIntent.getActivity(mContext, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(mContext);
    	builder.setSmallIcon(icon);
        builder.setContentTitle(title);
    	builder.setContentText(text);
    	builder.setContentIntent(pi);
    	builder.setOngoing(mOnGoing);
    	NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    	manager.notify(0, builder.build());
    }
}
