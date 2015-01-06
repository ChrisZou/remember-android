package com.chriszou.remember;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.chriszou.androidlibs.Toaster;
import com.chriszou.remember.model.Account;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

/**
 * Created by Chris on 1/2/15.
 */
@EActivity(R.layout.settings_layout)
public class SettingsActivity extends RmbActivity{

    @Click
    void logout() {
        Account.logout();
        Toaster.s(this, "已退出登录");

        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent(Account.BROADCAST_LOGOUT));
        startActivity(LoginActivity.createIntent(this));
        finish();
    }

    public static Intent createIntent(Activity activity) {
        return new Intent(activity, SettingsActivity_.class);
    }
}
