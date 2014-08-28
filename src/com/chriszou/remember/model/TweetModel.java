/**
 * TweetModel.java
 * 
 * Created by zouyong on Aug 4, 2014,2014
 */
package com.chriszou.remember.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;

import com.chriszou.androidlibs.L;
import com.chriszou.androidlibs.UrlContentLoader;
import com.chriszou.androidlibs.UrlContentLoader.CallBack;

/**
 * @author zouyong
 * 
 */
public class TweetModel {
    final String SERVER_IP = "112.124.121.155";
	final String LISTING_URL_JSON = "http://"+SERVER_IP+":3000/tweets.json";
	final String INDEX_URL = "http://"+SERVER_IP+":3000/tweets";
    final String TWEET_CREATING_URL = "http://"+SERVER_IP+":3000/tweet/create";

	public void loadTweets(CallBack callBack) {
		UrlContentLoader loader = new UrlContentLoader(LISTING_URL_JSON);
		loader.execute(callBack);
	}

    private BasicCredentialsProvider getCredentialsProvider() {
		//Create our AuthScope
	    AuthScope scope = new AuthScope(SERVER_IP, 3000);
	    String username = "chris";
        String password = "chuang";
	    //Set Credentials
	    UsernamePasswordCredentials myCredentials = new UsernamePasswordCredentials( username, password );
	    //Set Provider
	    BasicCredentialsProvider provider = new BasicCredentialsProvider();
	    provider.setCredentials(scope, myCredentials);
        
	    return provider;
    }
    
	public void addTweet(final String text) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					nameValuePairs.add(new BasicNameValuePair("tweet[content]", text));
                    
					HttpPost post = new HttpPost(TWEET_CREATING_URL);
					post.setHeader("User-Agent", "Remember Android");
					post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

					DefaultHttpClient client = new DefaultHttpClient();
                    client.setCredentialsProvider(getCredentialsProvider());
					client.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "RememberAndroidApp");
					client.execute(post);
                    
				} catch (ClientProtocolException e) {
                    L.l(e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
                    L.l(e.getMessage());
					e.printStackTrace();
				}
			}
		};
        
		new Thread(runnable).start();
	}
}
