package com.chriszou.remember.model;

import com.chriszou.androidlibs.TimeHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Content presenting mode;
 * Created by Chris on 1/7/15.
 */
public enum ContentMode {
    /**
     * Present today's tweets
     */
    TODAY {
        @Override
        public List<Tweet> getTweets(List<Tweet> tweets) {
            List<Tweet> result = new ArrayList<Tweet>();
            String todayString = TimeHelper.getTodayString();
            for (Tweet item : tweets) {
                String createdTime = item.getCreatedTime();
                if (createdTime.startsWith(todayString)) {
                    result.add(item);
                }
            }
            return result;
        }
    },
    /**
     * Shuffle the tweets
     */
    SHUFFLE {
        @Override
        public List<Tweet> getTweets(List<Tweet> tweets) {
            Collections.shuffle(tweets, new Random(System.nanoTime()));
            return tweets;
        }
    };

    public abstract List<Tweet> getTweets(List<Tweet> tweets);
}
