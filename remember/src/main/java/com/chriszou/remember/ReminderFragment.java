/**
 * ShufferActivity.java
 *
 * Created by zouyong on Aug 9, 2014,2014
 */
package com.chriszou.remember;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.chriszou.androidlibs.TimeHelper;
import com.chriszou.remember.model.Tweet;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author zouyong
 *
 */
@EFragment(R.layout.shuffer_activity)
public class ReminderFragment extends TweetListFragment {

    public static final String EXTRA_STRING_REMINDER_TYPE = "extra_string_reminder_type";

    @Override
    protected ListView getListView() {
        return mListView;
    }

    public static enum ReminderType{
    	SHUFFLE, //Random the list
        TODAY;  //Show today's tweets
    }

	@ViewById(R.id.shuffer_listview)
	ListView mListView;

	@AfterViews
	void loadData() {
        loadTweets();
	}

    @Override
    protected List<Tweet> preprocessTweets(List<Tweet> tweets) {
        return getShowList(tweets);
    }

    /**
	 * According to the reminder type, get the sublist to show in the UI
	 * @return
	 */
	private List<Tweet> getShowList(List<Tweet> items) {
		String typeString = getArguments().getString(EXTRA_STRING_REMINDER_TYPE, ReminderType.SHUFFLE.name());
		ReminderType type = ReminderType.valueOf(typeString);
        List<Tweet>result = null;
		switch (type){
		case SHUFFLE:
            result = items;
            Collections.shuffle(result,new Random(System.nanoTime()));
            break;
		case TODAY:
			result = getTodayTweets(items);
            break;
		}

		return result;
	}

	/**
	 * Get the tweets that was created today
	 *
	 * @param items
	 * @return
	 */
	private List<Tweet> getTodayTweets(List<Tweet> items) {
		List<Tweet> result = new ArrayList<Tweet>();
		String todayString = TimeHelper.getTodayString();
		for (Tweet item : items) {
			String createdTime = item.getContent();
			if (createdTime.startsWith(todayString)) {
				result.add(item);
			}
		}
		return result;
	}

    @Override
    public void bindView(int position, View view, Tweet item, ViewGroup parent) {
        TextView textView= (TextView)view;
        textView.setText(item.getContent());
        //Hightlight the first 5 items, means at least you should review these 5 items;
        int bgColor = position< 5 ? Color.parseColor("#D6E1A4") : Color.TRANSPARENT;
        textView.setBackgroundColor(bgColor);
    }
}
