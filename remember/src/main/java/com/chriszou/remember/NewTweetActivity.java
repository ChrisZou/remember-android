package com.chriszou.remember;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.chriszou.androidlibs.Toaster;
import com.chriszou.androidlibs.ViewUtils;
import com.chriszou.remember.model.Tweet;
import com.chriszou.remember.model.TweetModel;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

/**
 * Created by Chris on 1/8/15.
 */
@EActivity(R.layout.new_tweet_activity)
public class NewTweetActivity extends RmbActivity {
    public static final String EXTRA_SERIAL_TWEET = "extra_serial_tweet";

    @ViewById(R.id.new_tweet_edit)
    EditText mEditText;
    @ViewById(R.id.new_tweet_confirm)
    Button mBtnConfirm;

    @AfterViews
    void initViews() {
        updateBtnState();
    }

    @Click
    void newTweetConfirm() {
        String content = ViewUtils.getTextTrimed(mEditText);
        performCreate(content);
    }

    @Background
    void performCreate(String content) {
        Tweet tweet = new Tweet(content);
        try {
            boolean result = new TweetModel().addTweet(tweet);
            onCreateResult(result, tweet);
        } catch (IOException e) {
            e.printStackTrace();
            onCreateResult(false, tweet);
        }
    }

    @UiThread
    void onCreateResult(boolean succeed, Tweet tweet) {
        int msg = succeed? R.string.create_succeed : R.string.create_failed;
        Toaster.s(getActivity(), msg);

        if (succeed) {
            Intent i = new Intent();
            i.putExtra(EXTRA_SERIAL_TWEET, tweet);
            finish(RESULT_OK, i);
        }
    }

    @AfterTextChange(R.id.new_tweet_edit)
    void updateBtnState() {
        mBtnConfirm.setEnabled(ViewUtils.inputNotEmpty(mEditText));
    }
    public static Intent createIntent(Activity activity) {
        return new Intent(activity, NewTweetActivity_.class);
    }
}
