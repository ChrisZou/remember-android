package com.chriszou.remember;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;

import com.chriszou.remember.model.Account;
import com.chriszou.remember.util.AlarmUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {
	public static final String EXTRA_BOOL_REMINDER = "extra_bool_reminder";
    private static final int REQUEST_LOGIN = 1;

    @AfterViews
    void checkLogin() {
        if (!loggedIn()) {
            login();
        } else {
            showContent();
        }
    }

    private void showContent() {
        // Whether to start this activity as a reminder
        boolean reminder = getIntent().getBooleanExtra(EXTRA_BOOL_REMINDER, false);
        Fragment fragment;
        if(reminder) {
            fragment = new ReminderFragment_();
            fragment.setArguments(getIntent().getExtras());
        } else {
            fragment = new MainFragment_();
            AlarmUtils.setupAlarms(this);
        }
        getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
    }

    @OnActivityResult(REQUEST_LOGIN)
    void loginResult() {
        if (loggedIn()) {
            showContent();
        }
    }



    private void login() {
        Intent intent = new Intent(this, LoginActivity_.class);
        startActivityForResult(intent, REQUEST_LOGIN);
    }

    private boolean loggedIn() {
        return Account.loggedIn();
    }
}
