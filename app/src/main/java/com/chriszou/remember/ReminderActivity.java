package com.chriszou.remember;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.chriszou.androidlibs.TimeHelper;
import com.chriszou.remember.model.ContentMode;
import com.chriszou.remember.model.Tweet;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Chris on 1/8/15.
 */
@EActivity(R.layout.shuffer_activity)
public class ReminderActivity extends TweetListActivity {

    public static final String EXTRA_STRING_REMINDER_TYPE = "extra_string_reminder_type";

    @Extra(EXTRA_STRING_REMINDER_TYPE)
    String mReminderType;

    @Override
    protected ListView getListView() {
        return mListView;
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
        ContentMode mode = ContentMode.valueOf(mReminderType);
        return mode.getTweets(items);
    }

    @Override
    public void bindView(int position, View view, Tweet item, ViewGroup parent) {
        TextView textView= (TextView)view;
        textView.setText(item.getContent());
        //Hightlight the first 5 items, means at least you should review these 5 items;
        int bgColor = position< 5 ? Color.parseColor("#D6E1A4") : Color.TRANSPARENT;
        textView.setBackgroundColor(bgColor);
    }

    public static Intent createIntent(Context context, String reminderType) {
        Intent i = new Intent(context, ReminderActivity_.class);
        i.putExtra(EXTRA_STRING_REMINDER_TYPE, reminderType);
        return i;
    }
}
