package com.chriszou.remember;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ListView;

import com.chriszou.remember.model.Tweet;
import com.chriszou.remember.model.UserModel;
import com.chriszou.remember.util.ActivityNavigator;
import com.chriszou.remember.util.ReminderAlarmHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.fragment_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends TweetListActivity {
    private static final int REQUEST_NEW_TWEET = 1;
    private LocalBroadcastManager mLocalBroadcastManager;

    @AfterViews
    void checkLogin() {
        setActionBarHomeUp(false);
        registerLocalBroadcast();

        if (!UserModel.loggedIn()) {
            login();
        } else {
            checkUpgrade();
            ReminderAlarmHelper.setupAlarms(this);
            loadTweets();
        }

    }

    @Background
    void checkUpgrade() {
//        AppUpgrader.checkUpgrade(getActivity());
    }

    private void login() {
        ActivityNavigator.toLoginActivity(getActivity());
        finish();
    }

    private void registerLocalBroadcast() {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter(UserModel.BROADCAST_LOGOUT);
        mLocalBroadcastManager.registerReceiver(mLogoutReceiver, intentFilter);
    }

    @OptionsItem
    void actionSettings() {
        startActivity(SettingsActivity.createIntent(getActivity()));
    }

    @OptionsItem
    void actionNew() {
        startActivityForResult(NewTweetActivity.createIntent(getActivity()), REQUEST_NEW_TWEET);
    }

    @ViewById(R.id.main_listview)
    ListView mListView;

    @Override
    protected ListView getListView() {
        return mListView;
    }

    @OnActivityResult(REQUEST_NEW_TWEET)
    void onResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Tweet tweet = (Tweet) data.getSerializableExtra(NewTweetActivity.EXTRA_SERIAL_TWEET);
            getAdapter().add(0, tweet);
        }
    }

    private BroadcastReceiver mLogoutReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction()!=null) {
                String action = intent.getAction();
                if (action.equals(UserModel.BROADCAST_LOGOUT)) {
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        mLocalBroadcastManager.unregisterReceiver(mLogoutReceiver);
        super.onDestroy();
    }

    public static Intent createIntent(Activity activity) {
        return new Intent(activity, MainActivity_.class);
    }
}
