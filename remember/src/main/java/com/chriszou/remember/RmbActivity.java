package com.chriszou.remember;

import android.app.Activity;
import android.content.Intent;

/**
 * Parent activity for all the activities in this application
 * Created by Chris on 1/2/15.
 */
public abstract class RmbActivity extends Activity {

    public Activity getActivity() {
        return this;
    }

}
