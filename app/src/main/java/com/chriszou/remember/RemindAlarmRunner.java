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
import com.chriszou.remember.model.Tweet;
import com.chriszou.remember.model.TweetModel;
import com.chriszou.remember.util.ReminderAlarmHelper;

import java.util.List;

/**
 * @author zouyong
 *
 */
public class RemindAlarmRunner implements AlarmRunner {
    private Intent intent;
    private Context mContext;
	/* (non-Javadoc)
	 * @see com.chriszou.androidlibs.AlarmRunner#run(android.content.Context, android.content.Intent)
	 */
	@Override
	public void run(final Context context, Intent intent) {
        this.intent = intent;
        mContext = context;
        TweetModel.getInstance().allTweets().subscribe(this::showReminderTweets, e ->{});
		ReminderAlarmHelper.setupAlarms(context);
	}

    private void showReminderTweets(final List<Tweet> tweets) {
        final String reminderType = intent.getBundleExtra(AlarmHelper.EXTRA_EXTRA).getString(ReminderActivity.EXTRA_STRING_REMINDER_TYPE, ContentMode.SHUFFLE.name());
        ContentMode mode = ContentMode.valueOf(reminderType);
        List<Tweet> reminderTweets = mode.getTweets(tweets);
        if (reminderTweets.size() > 0) {
            Intent activityIntent = ReminderActivity.createIntent(mContext, reminderType);
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(activityIntent);
        }
    }

}
