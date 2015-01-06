package com.chriszou.remember;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.support.v4.content.LocalBroadcastManager;

import com.chriszou.androidlibs.L;
import com.chriszou.remember.model.Account;
import com.chriszou.remember.util.AlarmUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends RmbActivity {
	public static final String EXTRA_BOOL_REMINDER = "extra_bool_reminder";
    private static final int REQUEST_LOGIN = 1;

    private LocalBroadcastManager mLocalBroadcastManager;

    @AfterViews
    void checkLogin() {
        registerLocalBroadcast();

        if (!loggedIn()) {
            login();
        } else {
            showContent();
        }
    }

    private void registerLocalBroadcast() {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        mLocalBroadcastManager.registerReceiver(mLogoutReceiver, new IntentFilter(Account.BROADCAST_LOGOUT));
    }

    @OptionsItem
    void actionSettings() {
        startActivity(SettingsActivity.createIntent(getActivity()));
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

    @Override
    protected void onDestroy() {
        mLocalBroadcastManager.unregisterReceiver(mLogoutReceiver);
        super.onDestroy();
    }

    private BroadcastReceiver mLogoutReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && Account.BROADCAST_LOGOUT.equals(intent.getAction())) {
                finish();
            }
        }
    };

    public static Intent createIntent(Activity activity) {
        return new Intent(activity, MainActivity_.class);
    }
}
