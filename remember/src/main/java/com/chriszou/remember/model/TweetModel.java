/**
 * TweetModel.java
 *
 * Created by zouyong on Aug 4, 2014,2014
 */
package com.chriszou.remember.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;

import android.content.Context;
import android.preference.PreferenceManager;

import com.chriszou.androidlibs.L;
import com.chriszou.androidlibs.Prefs;
import com.chriszou.androidlibs.UrlContentLoader;
import com.chriszou.androidlibs.UrlContentLoader.Callback;

/**
 * @author zouyong
 *
 */
public class TweetModel {
	final String SERVER_IP = "112.124.121.155";
	final String LISTING_URL_JSON = "http://"+SERVER_IP+"/tweets_mobile.json";
	final String TWEET_CREATING_URL = "http://"+SERVER_IP+"/tweets_mobile";

	private static final String PREF_STRING_ETAG = "pref_string_etag";
	private static final String PREF_KEY_STRING_TWEET = "pref_key_string_tweet";

	public void loadTweets(Callback callBack) {
		if(isUpdated()) {
			UrlContentLoader loader = new UrlContentLoader(LISTING_URL_JSON);
			loader.execute(callBack);
		}
	}

	private boolean isUpdated() {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpHead head = new HttpHead(LISTING_URL_JSON);
			HttpResponse response  = client.execute(head);
			String etag = response.getFirstHeader("ETag").getValue();
			String oldEtag = Prefs.getString(PREF_STRING_ETAG, "");
			if(oldEtag.equals(etag)) {
				return false;
			}
			Prefs.putString(PREF_STRING_ETAG, etag);
			return true;
		} catch (Exception e) {
			L.l("Exception: "+e.getMessage());
			e.printStackTrace();
		}
		return true;
	}

	public String getTweetCache(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_KEY_STRING_TWEET, null);
	}

	public void addTweet(final String text) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					nameValuePairs.add(new BasicNameValuePair("tweet[content]", text));
					HttpPost post = new HttpPost(TWEET_CREATING_URL);
					post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

					DefaultHttpClient client = new DefaultHttpClient();
					client.execute(post);

				} catch (ClientProtocolException e) {
					L.e(e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					L.e(e.getMessage());
					e.printStackTrace();
				}
			}
		};

		new Thread(runnable).start();
	}

	/**
	 * @param context
	 * @param content
	 */
	public void saveCache(Context context, String content) {
		Prefs.putString(PREF_KEY_STRING_TWEET, content);
	}
}
