/**
 * TweetModel.java
 *
 * Created by zouyong on Aug 4, 2014,2014
 */
package com.chriszou.remember.model;

import android.text.TextUtils;

import com.chriszou.androidlibs.HttpUtils;
import com.chriszou.androidlibs.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
    final String DEV_SERVER = "10.197.32.129:3000";
    final String REAL_SERVER = "112.124.121.155";
    final String SERVER_URL = "http://" + DEV_SERVER;
//    final String SERVER_URL = "http://"+ REAL_SERVER;

    final String LISTING_URL_JSON = SERVER_URL + "/tweets_mobile.json";
    final String TWEET_CREATING_URL = SERVER_URL + "/tweets_mobile";

    private static final String PREF_STRING_ETAG = "pref_string_etag";
    private static final String PREF_KEY_STRING_TWEET = "pref_key_string_tweet";

    public List<Tweet> allTweets() throws IOException {
        String authToken = Account.getAuthToken();
        if (TextUtils.isEmpty(authToken)) {
            return Collections.EMPTY_LIST;
        }

        if (isUpdated()) {
            String tweetsJson = HttpUtils.getContent(LISTING_URL_JSON + "/?auth_token=" + authToken);
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
        String etag = HttpUtils.getEtag(LISTING_URL_JSON);
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
        return tweetsJson==null ? Collections.EMPTY_LIST : jsonArrayToTweetList(tweetsJson);
    }

    public void addTweet(final Tweet tweet) throws IOException {
        HttpUtils.postJson(TWEET_CREATING_URL, tweet.toJson());
    }

    /**
     * @param content
     */
    public void saveCache(String content) {
        Prefs.putString(PREF_KEY_STRING_TWEET, content);
    }
}
