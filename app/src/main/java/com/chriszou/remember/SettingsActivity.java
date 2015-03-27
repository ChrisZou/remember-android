package com.chriszou.remember;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.TextView;

import com.chriszou.androidlibs.Toaster;
import com.chriszou.androidlibs.UIHandler;
import com.chriszou.remember.model.UserModel;
import com.chriszou.remember.model.TweetModel;
import com.chriszou.remember.model.User;
import com.chriszou.remember.util.ActivityNavigator;
import com.chriszou.remember.util.Links;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Chris on 1/2/15.
 */
@EActivity(R.layout.settings_layout)
public class SettingsActivity extends RmbActivity{
    @ViewById
    TextView usernameView;
    @ViewById
    TextView noteCountView;

    @ViewById
    CircleImageView avatarView;

    private User mUser;

    @AfterViews
    void initViews() {
        if (assureLoggedIn()) {
            updateView();
        }
    }

    private void updateView() {
        Picasso.with(getActivity()).load(Links.userAvatar(mUser)).into(avatarView);
        usernameView.setText(mUser.username);
        setTweetCount();
    }

    void setTweetCount() {
        new Thread(() -> {
            try { final int count = TweetModel.getInstance().allTweets().size();
                UIHandler.post(() -> noteCountView.setText("您共有 "+count+" 条笔记"));
            } catch (IOException e) {e.printStackTrace();}
        }).start();
    }

    private boolean assureLoggedIn() {
        mUser = UserModel.currentUser();
        if (mUser == null) {
            Toaster.s(getActivity(), "登陆已失效，请重新登录");
            ActivityNavigator.toLoginActivity(getActivity());
            finish();
            return false;
        }
        return true;
    }

    @Click
    void accountLayout() {
        startActivity(new Intent(getActivity(),AccountInfoActivity_.class));
    }

    @Click
    void settingsReminders() {
        startActivity(ReminderListActivity.createIntent(getActivity()));
    }

    @Click
    void logout() {
        UserModel.logout();
        Toaster.s(this, "已退出登录");

        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent(UserModel.BROADCAST_LOGOUT));
        ActivityNavigator.toLoginActivity(getActivity());
        finish();
    }

    public static Intent createIntent(Activity activity) {
        return new Intent(activity, SettingsActivity_.class);
    }
}
