package com.chriszou.remember;

import com.activeandroid.ActiveAndroid;
import com.chriszou.androidlibs.Prefs;
import com.chriszou.androidlibs.UtilApplication;
import com.chriszou.remember.util.DBUtils;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EApplication;

/**
 * Created by Chris on 1/7/15.
 */
@EApplication
public class RmbApplication extends UtilApplication {
    private static final String PREF_BOOL_FIRST_INSTALL = "pref_bool_first_install";

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        firstInstallInit();
    }

    @Background
    void firstInstallInit() {
        boolean firstInstall = Prefs.getBoolean(PREF_BOOL_FIRST_INSTALL, true);
        if (firstInstall) {
            DBUtils.initFirstInstall();

            Prefs.putBoolean(PREF_BOOL_FIRST_INSTALL, false);
        }

    }
}
