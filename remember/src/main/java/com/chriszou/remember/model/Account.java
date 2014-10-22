package com.chriszou.remember.model;

import android.util.Log;

import com.chriszou.androidlibs.L;
import com.chriszou.androidlibs.Prefs;
import com.chriszou.androidlibs.UrlUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by zouyong on 10/20/14.
 */
public class Account {

    private static final String SERVER_URL = "http://10.197.32.129:3000/";
    private static final String LOGIN_URL = SERVER_URL + "mobile_session";
    private static final String LOGOUT_ULR = LOGIN_URL;

    private static final String PREF_STRING_AUTH_TOKEN = "pref_string_auth_token";
    public static boolean loggedIn() {
        return savedToken()!=null;
    }

    private static String savedToken() {
        return Prefs.getString(PREF_STRING_AUTH_TOKEN, null);
    }

    public static void login(String email, String password, LoginCallback callback) {

        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 5 * 1000);
        HttpConnectionParams.setSoTimeout(params, 5*1000);

        DefaultHttpClient client = new DefaultHttpClient(params);
        HttpPost post = new HttpPost(LOGIN_URL);
        L.l("login: " + " email:" + email + ", password: " + password);
        try {
            // add the user email and password to the params
            JSONObject userObj = new JSONObject();
            userObj.put("email", email);
            userObj.put("password", password);
            StringEntity se = new StringEntity(userObj.toString());
            post.setEntity(se);

            // setup the request headers
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-Type", "application/json");

            HttpResponse response = client.execute(post);
            if (response!=null) {
                String responseString = UrlUtils.responseToString(response);
                JSONObject json = new JSONObject(responseString);
                if (response.getStatusLine().getStatusCode() == 200) {
                    String authToken = json.optJSONObject("data").optString("auth_token");
                    Prefs.putString(PREF_STRING_AUTH_TOKEN, authToken);
                    callback.onLoginResult(true, null);
                } else {
                    callback.onLoginResult(false, json.optString("error_msg"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            callback.onLoginResult(false, e.getMessage());
        }

    }

    public static interface LoginCallback {
        public void onLoginResult(boolean succeed, String errorMsg);
    }
}
