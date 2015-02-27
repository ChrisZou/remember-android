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
    public final void onErrorResponse(VolleyError error) {
        try {
            L.l("Get error: "+error);
            error.printStackTrace();
            String data = new String(error.networkResponse.data);
            ErrorResponse errorResponse = new ErrorResponse(new JSONObject(data));
            handleError(errorResponse);
        } catch (Exception e) {
            e.printStackTrace();
            handleError(null);
        }
    }

    protected abstract void handleError(ErrorResponse errorResponse);

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
