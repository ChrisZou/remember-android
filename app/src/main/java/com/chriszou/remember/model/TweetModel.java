/**
 * TweetModel.java
 *
 * Created by zouyong on Aug 4, 2014,2014
 */
package com.chriszou.remember.model;

import com.chriszou.androidlibs.HttpUtils;
import com.chriszou.androidlibs.Prefs;
import com.chriszou.remember.util.Links;

import org.apache.http.HttpResponse;
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
    private static final String PREF_STRING_ETAG = "pref_string_etag";
    private static final String PREF_KEY_STRING_TWEET = "pref_key_string_tweet";

    private static TweetModel sTweetModel = new TweetModel();

    private TweetModel() {}

    public static TweetModel getInstance() {
        return sTweetModel;
    }

    public List<Tweet> allTweets() throws IOException {
        if (UserModel.currentUser() == null) {
            return new ArrayList<Tweet>();
        }

        if (isUpdated()) {
            String url = Links.tweetsUrl(UserModel.currentUser());
            String tweetsJson = HttpUtils.getContent(url);
            return jsonArrayToTweetList(tweetsJson);
        } else {
            return getCachedTweets();
        }
    }

    private List<Tweet> jsonArrayToTweetList(String tweetsJson) {
        List<Tweet> results = new ArrayList<Tweet>();
        try {
            JSONArray array = new JSONObject(tweetsJson).optJSONArray("data");
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
        String etag = HttpUtils.getEtag(Links.tweetsUrl(null));
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

    public boolean addTweet(final Tweet tweet) throws IOException {
        HttpResponse response = HttpUtils.postJson(Links.tweetsUrl(UserModel.currentUser()), tweet.toJson());
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200 || statusCode == 201) {
            return true;
        }
        return false;
    }

    /**
     * @param content
     */
    public void saveCache(String content) {
        Prefs.putString(PREF_KEY_STRING_TWEET, content);
    }

    public boolean createTweet(String content) throws IOException {
        Tweet tweet = new Tweet(content);
        return addTweet(tweet);
    }
}
