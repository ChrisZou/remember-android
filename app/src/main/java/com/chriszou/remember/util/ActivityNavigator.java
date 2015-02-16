package com.chriszou.remember.util;

import android.app.Activity;

import com.chriszou.remember.LoginActivity;
import com.chriszou.remember.MainActivity;

/**
 * Created by Chris on 1/7/15.
 */
public class ActivityNavigator {
    public static void toLoginActivity(Activity fromActivity) {
        fromActivity.startActivity(LoginActivity.createIntent(fromActivity));
    }

    public static void toMainActivity(Activity activity) {
        activity.startActivity(MainActivity.createIntent(activity));
    }
}
