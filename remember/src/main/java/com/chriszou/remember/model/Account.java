package com.chriszou.remember.model;

import com.chriszou.androidlibs.HttpUtils;
import com.chriszou.androidlibs.Prefs;
import com.google.gson.Gson;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouyong on 10/20/14.
 */
public class Account {

    public static final String BROADCAST_LOGOUT = "broadcast_logout";

    private static final String LOGIN_URL = ServerUtils.SERVER_ROOT + "/login";
    private static final String REGISTER_URL = ServerUtils.SERVER_ROOT + "/signup";

    private static final String PREF_STRING_AUTH_TOKEN = "pref_string_auth_token";
    public static boolean loggedIn() {
        return getAuthToken()!=null;
    }

    public static String getAuthToken() {
        return Prefs.getString(PREF_STRING_AUTH_TOKEN, null);
    }

    private static void saveToken(String token) {
        Prefs.putString(PREF_STRING_AUTH_TOKEN, token);
    }

    private static void removeToken() {
        Prefs.remove(PREF_STRING_AUTH_TOKEN);
    }

    public static void login(String email, String password, LoginCallback callback) {
        try {
            // add the user email and password to the params
            JSONObject userObj = new JSONObject();
            userObj.put("email", email);
            userObj.put("password", password);

            List<Header> headers = new ArrayList<Header>();
            headers.add(new BasicHeader(HttpUtils.HEADER_CONTENT_TYPE, HttpUtils.CONTENT_TYPE_JSON));

            HttpResponse response = HttpUtils.postJson(LOGIN_URL, userObj.toString(), headers);

            String responseString = HttpUtils.responseToString(response);
            JSONObject json = new JSONObject(responseString);

            if (response.getStatusLine().getStatusCode() == 201) {
                String authToken = json.optString("auth_token");
                saveToken(authToken);
                callback.onLoginResult(true, null);
            } else {
                callback.onLoginResult(false, json.optString("error_msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            callback.onLoginResult(false, e.getMessage());
        }
    }

    public static void register(User user, RegisterCallback callback) {
        try {
            // add the user email and password to the params
            String json = new Gson().toJson(user);

            List<Header> headers = new ArrayList<Header>();
            headers.add(new BasicHeader(HttpUtils.HEADER_CONTENT_TYPE, HttpUtils.CONTENT_TYPE_JSON));

            HttpResponse response = HttpUtils.postJson(REGISTER_URL, json, headers);

            String responseString = HttpUtils.responseToString(response);
            JSONObject responseJson = new JSONObject(responseString);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 201 || statusCode == 200) {
                String authToken = responseJson.optString("auth_token");
                saveToken(authToken);
                callback.onRegisterResult(true, null);
            } else {
                callback.onRegisterResult(false, responseJson.optString("error_msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            callback.onRegisterResult(false, e.getMessage());
        }
    }

    public static void logout() {
        removeToken();
    }

    public static interface LoginCallback {
        public void onLoginResult(boolean succeed, String errorMsg);
    }

    public static interface RegisterCallback {
        public void onRegisterResult(boolean succeed, String errorMsg);
    }
}
