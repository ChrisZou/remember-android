/**
 * RemindAlarmRunner.java
 *
 * Created by zouyong on Aug 19, 2014,2014
 */
package com.chriszou.remember;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.chriszou.androidlibs.AlarmHelper;
import com.chriszou.androidlibs.AlarmRunner;
import com.chriszou.remember.model.ContentMode;
import com.chriszou.remember.model.Tweet;
import com.chriszou.remember.model.TweetModel;
import com.chriszou.remember.util.ReminderAlarmHelper;

import java.io.IOException;
import java.util.List;

/**
 * @author zouyong
 *
 */
public class RemindAlarmRunner implements AlarmRunner {

	/* (non-Javadoc)
	 * @see com.chriszou.androidlibs.AlarmRunner#run(android.content.Context, android.content.Intent)
	 */
	@Override
	public void run(final Context context, Intent intent) {
		final String reminderType = intent.getBundleExtra(AlarmHelper.EXTRA_EXTRA).getString(ReminderActivity.EXTRA_STRING_REMINDER_TYPE,
				ContentMode.SHUFFLE.name());
        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                TweetModel model = new TweetModel();
                try {
                    List<Tweet> tweets = model.allTweets();
                    ContentMode mode = ContentMode.valueOf(reminderType);
                    tweets = mode.getTweets(tweets);
                    if (tweets.size() > 0) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Intent activityIntent = ReminderActivity.createIntent(context, reminderType);
                                activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(activityIntent);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();

		ReminderAlarmHelper.setupAlarms(context);
	}

}
