package com.chriszou.remember;

import android.animation.Animator;
import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.chriszou.androidlibs.L;
import com.chriszou.androidlibs.Toaster;
import com.chriszou.remember.model.Account;
import com.chriszou.remember.model.Tweet;
import com.chriszou.remember.model.TweetModel;
import com.chriszou.remember.util.ActivityNavigator;
import com.chriszou.remember.util.AlarmUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

import static com.chriszou.androidlibs.L.l;

@EActivity(R.layout.fragment_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends TweetListActivity {
    private LocalBroadcastManager mLocalBroadcastManager;

    @AfterViews
    void checkLogin() {
        setActionBarHomeUp(false);
        registerLocalBroadcast();

        if (!loggedIn()) {
            login();
        } else {
            loadTweets();
        }
    }

    private void registerLocalBroadcast() {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter(Account.BROADCAST_LOGOUT);
        mLocalBroadcastManager.registerReceiver(mLogoutReceiver, intentFilter);
    }

    @OptionsItem
    void actionSettings() {
        startActivity(SettingsActivity.createIntent(getActivity()));
    }

    @OptionsItem
    void actionNew() {
        startActivity(NewTweetActivity.createIntent(getActivity()));
    }

    @ViewById(R.id.main_listview)
    ListView mListView;

    @ViewById(R.id.main_btn_add)
    Button mAddBtn;
    @ViewById(R.id.main_add_edit)
    EditText mAddEdit;

    @Click(R.id.main_btn_add)
    void onAddClicked() {
        String text = mAddEdit.getText().toString().trim();
        if(text.length()==0) {
            return;
        }

        addTweet(text);
    }

    private void addTweet(String text) {
        try {
            new TweetModel().addTweet(new Tweet(text));
        } catch (IOException e) {
            Toaster.s(getActivity(), "Network connection failed");
        }
    }

    @Override
    protected ListView getListView() {
        return mListView;
    }

    private BroadcastReceiver mLogoutReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction()!=null) {
                String action = intent.getAction();
                if (action.equals(Account.BROADCAST_LOGOUT)) {
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                }
            }
        }
    };


    private void login() {
        ActivityNavigator.toLoginActivity(getActivity());
        finish();
    }

    private boolean loggedIn() {
        return Account.loggedIn();
    }

    @Override
    protected void onDestroy() {
        mLocalBroadcastManager.unregisterReceiver(mLogoutReceiver);
        super.onDestroy();
    }

    public static Intent createIntent(Activity activity) {
        return new Intent(activity, MainActivity_.class);
    }
}
