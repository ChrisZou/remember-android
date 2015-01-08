package com.chriszou.remember;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.chriszou.androidlibs.Toaster;
import com.chriszou.androidlibs.ViewUtils;
import com.chriszou.remember.model.TweetModel;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

/**
 * Created by Chris on 1/8/15.
 */
@EActivity(R.layout.new_tweet_activity)
public class NewTweetActivity extends RmbActivity{

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
        try {
            boolean result = new TweetModel().createTweet(content);
            int msg = result? R.string.create_succeed : R.string.create_failed;
            Toaster.s(getActivity(), msg);
        } catch (IOException e) {
            e.printStackTrace();
            Toaster.s(getActivity(), R.string.create_failed);
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
