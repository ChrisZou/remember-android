package com.chriszou.remember.model;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by Chris on 1/27/15.
 */
public class GsonUtils {
    private static final Gson sGson = new Gson();

    public static <T> T fromJson(JSONObject object, Class<T> clazz) {
        return sGson.fromJson(object.toString(), clazz);
    }

    public static String toJson(Object user) {
        return sGson.toJson(user);
    }
}
