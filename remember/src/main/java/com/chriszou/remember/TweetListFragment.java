package com.chriszou.remember;

import android.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.chriszou.androidlibs.Toaster;
import com.chriszou.androidlibs.ViewBinderAdapter;
import com.chriszou.androidlibs.ViewBinderAdapter.ViewBinder;
import com.chriszou.remember.model.Tweet;
import com.chriszou.remember.model.TweetModel;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;

import java.io.IOException;
import java.util.List;

/**
 * Parent Fragment that shows a list of tweets
 * Created by zouyong on 10/24/14.
 */
@EFragment
public abstract class TweetListFragment extends Fragment implements ViewBinder<Tweet> {

    @Background
    public void loadTweets() {
        /*
		 * First load cache, then load from network.
		 */
        TweetModel model = new TweetModel();
        List<Tweet> tweets = model.getCachedTweets();
        updateList(tweets);
        try {
            tweets = model.allTweets();
            updateList(tweets);
        } catch (IOException e) {
            Toaster.s(getActivity(), "Network connection failed");
            e.printStackTrace();
        }
    }

    protected abstract ListView getListView();

    @UiThread
    void updateList(List<Tweet> tweets) {
        tweets = preprocessTweets(tweets);
        ViewBinderAdapter<Tweet> adapter = new ViewBinderAdapter<Tweet>(getActivity(), tweets, R.layout.tweet_item, this);
        getListView().setAdapter(adapter);
    }

    /**
     * Allow subclass to preprocess the tweet list, like sort, filter, etc.
     */
    protected List<Tweet> preprocessTweets(List<Tweet> tweets) {
        return tweets;
    }

    @Override
    public void bindView(int position, View view, Tweet item, ViewGroup parent) {
        ((TextView)view).setText(item.getContent());
    }


}