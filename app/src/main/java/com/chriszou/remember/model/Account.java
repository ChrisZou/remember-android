package com.chriszou.remember.model;

import com.chriszou.androidlibs.HttpUtils;
import com.chriszou.androidlibs.L;
import com.chriszou.androidlibs.Prefs;
import com.chriszou.remember.util.ErrorResponse;
import com.chriszou.remember.util.Links;
import com.chriszou.remember.util.NetworkCallback;
import com.chriszou.remember.util.NetworkRequest;
import com.chriszou.remember.util.NetworkResponse;
import com.chriszou.remember.util.VolleyListener;
import com.google.gson.Gson;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.chriszou.androidlibs.L.l;

/**
 * Created by zouyong on 10/20/14.
 */
public class Account {

    public static final String BROADCAST_LOGOUT = "broadcast_logout";

    private static final String PREF_STRING_USER = "pref_string_user";

    public static boolean loggedIn() {
        return currentUser()!=null;
    }

    private static void saveUser(User user) {
        Prefs.putString(PREF_STRING_USER, GsonUtils.toJson(user));
    }

    private static void validateUserJson(String userString) {
        new Gson().fromJson(userString, User.class);
    }

    private static void removeUser() {
        Prefs.remove(PREF_STRING_USER);
    }

    public static void login(String email, String password, final NetworkCallback callback) {
        try {
            JSONObject data = new JSONObject();
            data.put("email", email);
            data.put("password", password);
            NetworkRequest.post(Links.LOGIN_URL, data, new VolleyListener() {
                @Override
                protected void handleError(ErrorResponse errorResponse) {
                    try {
                        l("error msg: %s", errorResponse.errorMsg);
                        callback.onRequestCompleted(false, errorResponse);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                protected void handleResponse(NetworkResponse networkResponse) {
                    try {
                        User user = GsonUtils.fromJson(networkResponse.data, User.class);
                        saveUser(user);
                        callback.onRequestCompleted(true, networkResponse);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            callback.onRequestCompleted(false, null);
        }
    }

    public static void register(User user, final NetworkCallback callback) {
        try {
            String json = new Gson().toJson(user);
            NetworkRequest.post(Links.SIGNUP_URL, new JSONObject(json), new VolleyListener() {
                @Override
                protected void handleError(ErrorResponse errorResponse) {
                    try {
                        l("error msg: %s", errorResponse.errorMsg);
                        callback.onRequestCompleted(false, errorResponse);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                protected void handleResponse(NetworkResponse networkResponse) {
                    try {
                        User user = GsonUtils.fromJson(networkResponse.data, User.class);
                        saveUser(user);
                        callback.onRequestCompleted(true, networkResponse);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            callback.onRequestCompleted(false, null);
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
}
