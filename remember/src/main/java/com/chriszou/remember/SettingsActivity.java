package com.chriszou.remember;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.TextView;

import com.chriszou.androidlibs.Toaster;
import com.chriszou.remember.model.Account;
import com.chriszou.remember.model.User;
import com.chriszou.remember.util.ActivityNavigator;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Chris on 1/2/15.
 */
@EActivity(R.layout.settings_layout)
public class SettingsActivity extends RmbActivity{
    @ViewById(R.id.settings_username_view)
    TextView mUsernameView;

    private User mUser;

    @AfterViews
    void initViews() {
        assureLoggedIn();
        mUsernameView.setText(Account.currentUser().email);
    }

    private void assureLoggedIn() {
        mUser = Account.currentUser();
        if (mUser == null) {
            Toaster.s(getActivity(), "登陆已失效，请重新登录");
            ActivityNavigator.toLoginActivity(getActivity());
            finish();
        }
    }

    @Click
    void logout() {
        Account.logout();
        Toaster.s(this, "已退出登录");

        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent(Account.BROADCAST_LOGOUT));
        ActivityNavigator.toLoginActivity(getActivity());
        finish();
    }

    public static Intent createIntent(Activity activity) {
        return new Intent(activity, SettingsActivity_.class);
    }
}
