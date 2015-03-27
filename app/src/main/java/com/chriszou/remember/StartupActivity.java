package com.chriszou.remember;

import android.os.Bundle;
import android.view.Window;

import com.chriszou.remember.model.UserModel;
import com.chriszou.remember.util.ActivityNavigator;

/**
 * Created by Chris on 1/9/15.
 */
public class StartupActivity extends RmbActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        if (UserModel.loggedIn()) {
            ActivityNavigator.toMainActivity(getActivity());
        } else {
            ActivityNavigator.toLoginActivity(getActivity());
        }

        finish();
    }
}
