package com.chriszou.remember.model;

import com.chriszou.androidlibs.Prefs;
import com.chriszou.remember.util.RetrofitUtils;
import com.google.gson.Gson;

import java.io.File;

import retrofit.http.Body;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedFile;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zouyong on 10/20/14.
 */
public class UserModel {

    public static final String BROADCAST_LOGOUT = "broadcast_logout";

    private static final String PREF_STRING_USER = "pref_string_user";

    public static boolean loggedIn() {
        return currentUser()!=null;
    }

    public static void saveUser(User user) {
        Prefs.putString(PREF_STRING_USER, GsonUtils.toJson(user));
    }

    private static void validateUserJson(String userString) {
        new Gson().fromJson(userString, User.class);
    }

    public static Observable<User> login(String email, String password) {
        User user = new User();
        user.email = email;
        user.password = password;
        return userService().login(user);
    }

    private static UserService sUserService;
    private static UserService userService() {
        if (sUserService == null) {
            sUserService = RetrofitUtils.restAdapter().create(UserService.class);
        }
        return sUserService;
    }

    public static Observable<User> register(User user) {
        return userService().register(user);
    }

    public static void logout() {
        removeUser();
    }
    private static void removeUser() {
        Prefs.remove(PREF_STRING_USER);
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

    public static Observable<User> setUsername(String username) {
        UserService service = RetrofitUtils.restAdapter().create(UserService.class);
        User user = new User();
        user.username = username;
        return service.updateUser(currentUser().id, currentUser().authToken, user).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }

    public static Observable<User> setUserAvater(String imageFilePath) {
        TypedFile avatar = new TypedFile("image/png", new File(imageFilePath));
        return userService().updateUser(currentUser().id, currentUser().authToken, avatar).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }

    public static interface UserService {
        @PUT("/users/{user_id}")
        Observable<User> updateUser(@Path("user_id") String userId, @Query("auth_token") String authToken, @Body User user);

        @POST("/login")
        Observable<User> login(@Body User user);

        @POST("/signup")
        Observable<User> register(@Body User user);

        @Multipart
        @PUT("/users/{user_id}")
        Observable<User> updateUser(@Path("user_id") String userId, @Query("auth_token") String authToken, @Part("avatar") TypedFile photo);
    }
}
