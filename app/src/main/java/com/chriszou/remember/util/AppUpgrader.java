package com.chriszou.remember.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.chriszou.androidlibs.Downloader;
import com.chriszou.androidlibs.UtilApplication;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;

import java.io.IOException;

import retrofit.http.GET;


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

            String[] localVersionBits = localVersion.split("\\.");
            String[] serverVersionBits = serverVersion.split("\\.");
            if (localVersionBits.length >= serverVersionBits.length) {
                for (int i = 0; i < serverVersionBits.length; i++) {
                    int sb = Integer.valueOf(serverVersionBits[i]);
                    int lb = Integer.valueOf(localVersionBits[i]);
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
            if (!shouldUpgrade(app.version)) return;
            String outputFilePath = Environment.getExternalStorageDirectory().getPath()+"/woaifuxi.apk";
            Downloader downloader = new Downloader(activity, app.downloadUrl, outputFilePath);
            downloader.setOnDownloadCompleteListener((succeed, outputFile) -> {
                if (succeed) promptInstall(activity, outputFile);
            });
            downloader.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void promptInstall(Activity activity,  String outputFile) {
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        installIntent.setDataAndType(Uri.parse("file://" + outputFile), "application/vnd.android.package-archive");
        activity.startActivity(installIntent);
    }

    private static ServerApp getServerVersion() throws IOException, JSONException {
        return RetrofitUtils.restAdapter().create(AppService.class).getServerApp();
    }

    public interface AppService {
        @GET("/app/android")
        ServerApp getServerApp();
    }

    class ServerApp {
        public String version;

        @SerializedName("download_link")
        public String downloadUrl;
    }
}
