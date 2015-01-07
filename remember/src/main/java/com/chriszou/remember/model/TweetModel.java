/**
 * TweetModel.java
 *
 * Created by zouyong on Aug 4, 2014,2014
 */
package com.chriszou.remember.model;

import android.text.TextUtils;

import com.chriszou.androidlibs.HttpUtils;
import com.chriszou.androidlibs.L;
import com.chriszou.androidlibs.Prefs;
import com.chriszou.remember.util.UrlLinks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for doing Tweets CRUD.
 * Most method in this class involve networking, ensure they are called from a thread other than UI thread
 *
 * @author zouyong
 */
public class TweetModel {
    private static final String PREF_STRING_ETAG = "pref_string_etag";
    private static final String PREF_KEY_STRING_TWEET = "pref_key_string_tweet";

    public List<Tweet> allTweets() throws IOException {
        if (Account.currentUser() == null) {
            return new ArrayList<Tweet>();
        }

        if (isUpdated()) {
            String url = UrlLinks.tweetsUrl(Account.currentUser());
            String tweetsJson = HttpUtils.getContent(url);
            return jsonArrayToTweetList(tweetsJson);
        } else {
            return getCachedTweets();
        }
    }

    private List<Tweet> jsonArrayToTweetList(String tweetsJson) {
        List<Tweet> results = new ArrayList<Tweet>();
        try {
            JSONArray array = null;
            array = new JSONArray(tweetsJson);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.optJSONObject(i);
                Tweet t = Tweet.fromJson(object);
                results.add(t);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return results;
    }

    /**
     * If data on the server has been updated, otherwise just use the cache rather than doing another request
     *
     * @return
     */
    private boolean isUpdated() throws IOException {
        String etag = HttpUtils.getEtag(UrlLinks.tweetsUrl(null));
        String oldEtag = Prefs.getString(PREF_STRING_ETAG, "");
        if (oldEtag.equals(etag)) {
            return false;
        } else {
            Prefs.putString(PREF_STRING_ETAG, etag);
            return true;
        }
    }

    public List<Tweet> getCachedTweets() {
        String tweetsJson = Prefs.getString(PREF_KEY_STRING_TWEET, null);
        return tweetsJson == null ? Collections.EMPTY_LIST : jsonArrayToTweetList(tweetsJson);
    }

    public void addTweet(final Tweet tweet) throws IOException {
        HttpUtils.postJson(UrlLinks.tweetsUrl(Account.currentUser()), tweet.toJson());
    }

    /**
     * @param content
     */
    public void saveCache(String content) {
        Prefs.putString(PREF_KEY_STRING_TWEET, content);
    }
}
