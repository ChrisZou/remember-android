package com.chriszou.remember.model;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouyong on 10/24/14.
 */
public class Tweet {
    private String content = "";
    private String createdTime = "";
    private String modifiedTime = "";
    private List<String> tags = new ArrayList<String>();

    public Tweet() {}

    public Tweet(String text) {
        content = text;
    }

    /**
     *
     */
    public static Tweet fromJson(JSONObject json) {
        //TODO map json to Tweet
        Tweet tweet = new Tweet();
        tweet.setContent(json.optString("content"));
        tweet.setCreatedTime(json.optString("created_time"));
        tweet.setModifiedTime(json.optString("modified_time"));
        return tweet;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        parseTags();
    }

    public List<String> getTags() {
        return tags;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    /**
     * Parse the tags according to its content
     */
    private void parseTags() {
        //TODO parse tags from tweet's content
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

}
