package com.chriszou.remember.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Chris on 1/3/15.
 */
public class User {
    public String id;
    public String email;
    public String password;

    @SerializedName("password_confirmation")
    public String passwordConfirmation;

    @SerializedName("created_at")
    public String createdTime;

    @SerializedName("updated_at")
    public String updatedTime = "";

    @SerializedName("auth_token")
    public String authToken;
}
