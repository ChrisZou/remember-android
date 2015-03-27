package com.chriszou.remember.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.converter.GsonConverter;

/**
 * Created by Chris on 3/27/15.
 */
public class RetrofitUtils {
    private static RestAdapter sRestAdapter;
    public static RestAdapter restAdapter() {
        if (sRestAdapter==null) {
            sRestAdapter = new RestAdapter.Builder().setEndpoint(Links.SERVER.getServerRoot()).setConverter(getConverter()).setLogLevel(LogLevel.FULL).build();
        }

        return sRestAdapter;
    }

    private static GsonConverter getConverter() {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new ItemTypeAdapterFactory())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        return new GsonConverter(gson);
    }


}
