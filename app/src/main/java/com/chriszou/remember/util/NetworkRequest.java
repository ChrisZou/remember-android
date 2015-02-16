package com.chriszou.remember.util;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

/**
 * Created by Chris on 1/2/15.
 */
public abstract class NetworkRequest {
    private String mPath;
    private int mMethod = 1;
    private JSONObject mData;

    public NetworkRequest(String path, int method, JSONObject data) {
        mPath = path;
        mMethod = method;
        mData = data;
    }

    public abstract HttpResponse execute();
}
