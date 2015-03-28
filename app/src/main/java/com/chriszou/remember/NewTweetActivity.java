package com.chriszou.remember;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.chriszou.androidlibs.ViewUtils;
import com.chriszou.remember.model.Tweet;
import com.chriszou.remember.model.TweetModel;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Chris on 1/8/15.
 */
@EActivity(R.layout.new_tweet_activity)
public class NewTweetActivity extends RmbActivity {
    public static final String EXTRA_SERIAL_TWEET = "extra_serial_tweet";

    @ViewById(R.id.new_tweet_edit)
    EditText mEditText;
    @ViewById(R.id.new_tweet_confirm)
    View mBtnConfirm;

    @AfterViews
    void initViews() {
        updateBtnState();
    }

    @Click
    void newTweetConfirm() {
        String content = ViewUtils.getTextTrimed(mEditText);
        performCreate(content);
    }

    void performCreate(String content) {
        Tweet tweet = new Tweet(content);
        TweetModel.getInstance().addTweet(tweet).subscribe(this::onTweetCreated, this::onError);
    }

    void onTweetCreated(Tweet tweet) {
        Intent i = new Intent();
        i.putExtra(EXTRA_SERIAL_TWEET, tweet);
        finish(RESULT_OK, i);
    }

    @AfterTextChange(R.id.new_tweet_edit)
    void updateBtnState() {
        mBtnConfirm.setEnabled(ViewUtils.inputNotEmpty(mEditText));
    }
    
    public static Intent createIntent(Activity activity) {
        return new Intent(activity, NewTweetActivity_.class);
    }
}
