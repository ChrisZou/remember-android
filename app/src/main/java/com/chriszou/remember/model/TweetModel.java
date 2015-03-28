/**
 * TweetModel.java
 *
 * Created by zouyong on Aug 4, 2014,2014
 */
package com.chriszou.remember.model;

import com.chriszou.remember.util.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    private TweetService mTweetService;
    private TweetModel() {
        mTweetService = RetrofitUtils.restAdapter().create(TweetService.class);
    }

    public static TweetModel getInstance() {
        return sTweetModel;
    }

    public Observable<List<Tweet>> allTweets() {
        return mTweetService.getAll(currentUserToken()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public List<Tweet> getCachedTweets() {
        return new ArrayList<Tweet>();
    }

    public Observable<Tweet> addTweet(final Tweet tweet) {
        return mTweetService.createTweet(currentUserToken(), tweet).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private String currentUserToken() {
        return UserModel.currentUser().authToken;
    }

    public Observable<String> remove(Tweet item) {
        return RetrofitUtils.restAdapter().create(TweetService.class).remove(currentUserToken(), item.getId())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public interface TweetService{
        @DELETE("/tweets/{id}")
        Observable<String> remove(@Query("auth_token") String authToken, @Path("id") int id);

        @GET("/tweets")
        Observable<List<Tweet>> getAll(@Query("auth_token") String authToken);

        @POST("/tweets")
        Observable<Tweet> createTweet(@Query("auth_token") String authToken, @Body Tweet tweet);
    }
}
