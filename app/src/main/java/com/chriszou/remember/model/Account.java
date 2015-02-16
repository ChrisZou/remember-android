package com.chriszou.remember.model;

import com.chriszou.androidlibs.HttpUtils;
import com.chriszou.androidlibs.Prefs;
import com.chriszou.remember.util.UrlLinks;
import com.google.gson.Gson;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouyong on 10/20/14.
 */
public class Account {

    public static final String BROADCAST_LOGOUT = "broadcast_logout";

    private static final String PREF_STRING_USER = "pref_string_user";

    public static boolean loggedIn() {
        return currentUser()!=null;
    }

    private static void saveUser(String userString) {
        validateUserJson(userString);
        Prefs.putString(PREF_STRING_USER, userString);
    }

    private static void validateUserJson(String userString) {
        new Gson().fromJson(userString, User.class);
    }

    private static void removeUser() {
        Prefs.remove(PREF_STRING_USER);
    }

    public static void login(String email, String password, LoginCallback callback) {
        try {
            // add the user email and password to the params
            JSONObject userObj = new JSONObject();
            userObj.put("email", email);
            userObj.put("password", password);

            List<Header> headers = new ArrayList<Header>();
            headers.add(new BasicHeader(HttpUtils.HEADER_CONTENT_TYPE, HttpUtils.CONTENT_TYPE_JSON));

            HttpResponse response = HttpUtils.postJson(UrlLinks.LOGIN_URL, userObj.toString(), headers);

            String responseString = HttpUtils.responseToString(response);
            if (response.getStatusLine().getStatusCode() == 201) {
                saveUser(responseString);
                callback.onLoginResult(true, null);
            } else {
                JSONObject json = new JSONObject(responseString);
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

            HttpResponse response = HttpUtils.postJson(UrlLinks.SIGNUP_URL, json, headers);

            String responseString = HttpUtils.responseToString(response);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 201 || statusCode == 200) {
                saveUser(responseString);
                callback.onRegisterResult(true, null);
            } else {
                JSONObject responseJson = new JSONObject(responseString);
                callback.onRegisterResult(false, responseJson.optString("error_msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            callback.onRegisterResult(false, e.getMessage());
        }
    }

    public static void logout() {
        removeUser();
    }

    public static User currentUser() {
        String userJson = Prefs.getString(PREF_STRING_USER, null);
        try {
            User user = new Gson().fromJson(userJson, User.class);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static interface LoginCallback {
        public void onLoginResult(boolean succeed, String errorMsg);
    }

    public static interface RegisterCallback {
        public void onRegisterResult(boolean succeed, String errorMsg);
    }
}
