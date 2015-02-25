package com.chriszou.remember.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.chriszou.androidlibs.Downloader;
import com.chriszou.androidlibs.Downloader.OnDownloadCompleteListener;
import com.chriszou.androidlibs.L;
import com.chriszou.androidlibs.UtilApplication;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.chriszou.androidlibs.L.l;


/**
 * Created by Chris on 2/24/15.
 */
public class AppUpgrader {

    /**
     * @param serverVersion
     */
    public static boolean shouldUpgrade(String serverVersion) {
        try {
            Context context = UtilApplication.getContext();
            String localVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;

            l("localversion: " + localVersion);
            String[] localVersionBits = localVersion.split("\\.");
            String[] serverVersionBits = serverVersion.split("\\.");
            if (localVersionBits.length >= serverVersionBits.length) {
                for (int i = 0; i < serverVersionBits.length; i++) {
                    int sb = Integer.valueOf(serverVersionBits[i]);
                    int lb = Integer.valueOf(localVersionBits[i]);
                    l("sb: %d, lb: %d", sb, lb);
                    if (sb > lb) {
                        return true;
                    }
                }
            } else {
                for (int i = 0; i < localVersionBits.length; i++) {
                    int sb = Integer.valueOf(serverVersionBits[i]);
                    int lb = Integer.valueOf(localVersionBits[i]);
                    if (sb < lb) {
                        return false;
                    }
                }
                return true;
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void checkUpgrade(final Activity activity) {
        try {
            ServerApp app = getServerVersion();
            if (shouldUpgrade(app.version)) {
                l("should upgrade");
                String outputPath = Environment.getExternalStorageDirectory().getPath();
                Downloader downloader = new Downloader(activity, app.downloadUrl, outputPath + "/woaifuxi.apk");
                downloader.setOnDownloadCompleteListenerListener(new OnDownloadCompleteListener() {
                    @Override
                    public void onDownloadComplete(Boolean succeed, String outputPath) {
                        Intent promptInstall = new Intent(Intent.ACTION_VIEW);
                        promptInstall.setDataAndType(Uri.parse("file://" + outputPath), "application/vnd.android.package-archive");
                        activity.startActivity(promptInstall);
                    }
                });
                downloader.start();
            } else {
                l("should not upgrade");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ServerApp getServerVersion() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Links.ANDROID_VERSION_URL).build();
        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();
        JSONObject data = new JSONObject(jsonString).optJSONObject("data");
        ServerApp app = new Gson().fromJson(data.toString(), ServerApp.class);
        return app;
    }

    class ServerApp {
        public String version;

        @SerializedName("download_link")
        public String downloadUrl;
    }
}
