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
import com.chriszou.remember.util.AppUpgrader;
import com.chriszou.remember.util.ReminderAlarmHelper;
import com.chriszou.remember.util.UMengUtils;
import com.yalantis.phoenix.PullToRefreshView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity(R.layout.fragment_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends TweetListActivity {
    private static final int REQUEST_NEW_TWEET = 1;
    private LocalBroadcastManager mLocalBroadcastManager;
    @ViewById
    PullToRefreshView pullToRefresh;

    @AfterViews
    void checkLogin() {
        setActionBarHomeUp(false);
        registerLocalBroadcast();

        if (!UserModel.loggedIn()) { login(); finish(); return; }

        getListView().setOnItemClickListener((parent, view, position, id) -> UMengUtils.logEvent(getActivity(), UMengUtils.EVENT_NOTE_ITEM_CLICKED));
        pullToRefresh.setOnRefreshListener(this::loadTweets);
        checkUpgrade();
        ReminderAlarmHelper.setupAlarms(this);
        loadTweets();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UMengUtils.logAppStartEvent(getActivity());
    }

    @Background
    void checkUpgrade() {
        AppUpgrader.checkUpgrade(getActivity());
    }

    private void login() {
        ActivityNavigator.toLoginActivity(getActivity());
    }

    private void registerLocalBroadcast() {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter(UserModel.BROADCAST_LOGOUT);
        mLocalBroadcastManager.registerReceiver(mLogoutReceiver, intentFilter);
    }

    @OptionsItem
    void actionSettings() {
        UMengUtils.logEvent(getActivity(), UMengUtils.EVENT_SETTINGS_CLICKED);
        startActivity(SettingsActivity.createIntent(getActivity()));
    }

    @OptionsItem
    void actionNew() {
        UMengUtils.logEvent(getActivity(), UMengUtils.EVENT_ADD_CLICKED);
        startActivityForResult(NewTweetActivity.createIntent(getActivity()), REQUEST_NEW_TWEET);
    }

    @Override
    protected void onTweetsLoaded(List<Tweet> tweets) {
        super.onTweetsLoaded(tweets);
        pullToRefresh.setRefreshing(false);
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
            if (intent != null && UserModel.BROADCAST_LOGOUT.equals(intent.getAction())) getActivity().finish();
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
