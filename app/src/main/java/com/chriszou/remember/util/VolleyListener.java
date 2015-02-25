package com.chriszou.remember.util;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.chriszou.androidlibs.L;

import org.json.JSONObject;

/**
 * Created by Chris on 2/24/15.
 */
public abstract class VolleyListener implements Listener<JSONObject>, ErrorListener {
    @Override
    public void onErrorResponse(VolleyError error) {
        L.l("error: " + error);
    }

    @Override
    public final void onResponse(JSONObject response) {
        try {
            NetworkResponse networkResponse = new NetworkResponse(response);
            handleResponse(networkResponse);
        } catch (Exception e) {
            e.printStackTrace();
            handleResponse(null);
        }
    }

    protected abstract void handleResponse(NetworkResponse networkResponse);


}
