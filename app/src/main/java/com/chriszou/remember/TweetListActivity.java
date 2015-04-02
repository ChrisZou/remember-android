package com.chriszou.remember;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.chriszou.androidlibs.ViewBinderAdapter.ViewBinder;
import com.chriszou.remember.adapters.TweetAdapter;
import com.chriszou.remember.model.Tweet;
import com.chriszou.remember.model.TweetModel;
import com.chriszou.remember.util.UMengUtils;

import org.androidannotations.annotations.EActivity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Chris on 1/8/15.
 */
@EActivity
public abstract class TweetListActivity extends RmbActivity implements ViewBinder<Tweet> {

    private TweetAdapter mAdapter;

    public void loadTweets() {
        /*
		 * First load cache, then load from network.
		 */
        List<Tweet> tweets = TweetModel.getInstance().getCachedTweets();
        updateList(tweets);
        TweetModel.getInstance().allTweets().subscribe(this::onTweetsLoaded, this::onError);
    }
    protected TweetAdapter getAdapter() {
        return mAdapter;
    }
    protected abstract ListView getListView();

    void updateList(List<Tweet> tweets) {
        tweets = preprocessTweets(tweets);
        mAdapter = new TweetAdapter(getActivity(), tweets);//new ViewBinderAdapter<>(getActivity(), tweets, this);
        getListView().setAdapter(mAdapter);
    }

    private void onTweetsLoaded(List<Tweet> tweets) {
        if (tweets==null) return;
        logTweetsLoaded(tweets);
        updateList(tweets);
    }

    private void logTweetsLoaded(List<Tweet> tweets) {
        HashMap data = new HashMap();
        data.put("activity", getClass().getSimpleName());
        data.put("notes_count", tweets.size()+"");
        UMengUtils.logEvent(getActivity(), UMengUtils.EVENT_NOTES_LOADED, data);
    }

    /**
     * Allow subclass to preprocess the tweet list, like sort, filter, etc.
     */
    protected List<Tweet> preprocessTweets(List<Tweet> tweets) {
        return tweets;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.tweet_item;
    }

    @Override
    public void bindView(int position, View view, Tweet item, ViewGroup parent) {
        ((TextView)view).setText(item.getContent());
    }
}
