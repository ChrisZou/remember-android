package com.chriszou.remember.util;

import com.chriszou.remember.model.User;

/**
 * Created by Chris on 1/7/15.
 */
public final class Links {
    public static final String HOST = "http://www.woaifuxi.com";
    public static final Server SERVER = Server.PRODUCTION;
    private static final String TWEETS_PATH = "/tweets";
    public static final String LOGIN_URL = SERVER.getServerRoot()+"/login";
    public static final String SIGNUP_URL = SERVER.getServerRoot()+"/signup";
    public static final String ANDROID_VERSION_URL = SERVER.getServerRoot()+"/app/android";
    public static final String ANDROID_DOWNLOAD = SERVER.getServerRoot()+"/download/android";

    public static String userAvatar(User user) {
        return HOST+user.avatarUrl;
    }

    public static enum Server {
        LOCAL("http://192.168.31.240"), PRODUCTION("http://woaifuxi.com/api");
        private String serverRoot;
        public String getServerRoot() {
            return serverRoot;
        }

        Server(String serverRoot) {
            this.serverRoot = serverRoot;
        }
    }

    private static final String SERVER_ROOT = "http://woaifuxi.com/api";

    public static String tweetsUrl(User user) {
        String url = SERVER.getServerRoot()+ TWEETS_PATH;
        if (user != null) {
            url += "?auth_token=" + user.authToken;
        }
        return url;
    }

}
