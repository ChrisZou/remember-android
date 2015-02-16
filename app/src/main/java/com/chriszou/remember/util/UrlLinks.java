package com.chriszou.remember.util;

import com.chriszou.remember.model.User;

/**
 * Created by Chris on 1/7/15.
 */
public final class UrlLinks {
    private static final Server SERVER = Server.PRODUCTION;
    private static final String TWEETS_PATH = "/tweets";
    public static final String LOGIN_URL = SERVER.getServerRoot()+"/login";
    public static final String SIGNUP_URL = SERVER.getServerRoot()+"/signup";

    enum Server {
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
