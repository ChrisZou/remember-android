package com.chriszou.remember.util;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.chriszou.androidlibs.DeviceUtils;
import com.chriszou.remember.BuildConfig;
import com.chriszou.remember.RmbApplication;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.chriszou.androidlibs.L.l;

/**
 * Created by Chris on 1/2/15.
 */
public class NetworkRequest extends JsonObjectRequest{

    private static RequestQueue sRequestQueue = Volley.newRequestQueue(RmbApplication.getContext());

    private NetworkRequest(int method, String url, JSONObject jsonRequest, Listener<JSONObject> listener, ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public static void get(String url, VolleyListener volleyListener) {
        NetworkRequest request = new NetworkRequest(Method.GET, url, null, volleyListener, volleyListener);
        request.execute();
    }

    public static void post(String url, JSONObject data, VolleyListener listener) {
        NetworkRequest request = new NetworkRequest(Method.POST, url, data, listener, listener);
        request.execute();
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();
        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }

        addAppHeaders(headers);

        return super.getHeaders();
    }

    private void addAppHeaders(Map<String, String> headers) {
        headers.put("APP-PLATFORM", "android");
        headers.put("APP-VERSION-NAME", BuildConfig.VERSION_NAME);
        headers.put("APP-VERSION-CODE", BuildConfig.VERSION_CODE+"");
    }

    public void execute() {
        sRequestQueue.add(this);
        String url = String.format("Request url: %s %s", VolleyUtils.getMethodName(getMethod()), getUrl());
        l(url);
    }
}
