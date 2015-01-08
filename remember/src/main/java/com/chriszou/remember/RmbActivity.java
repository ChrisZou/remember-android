package com.chriszou.remember;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

/**
 * Parent activity for all the activities in this application
 * Created by Chris on 1/2/15.
 */
public abstract class RmbActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarHomeUp(true);
    }

    protected void setActionBarHomeUp(boolean homeAsUp) {
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(homeAsUp);
            getActionBar().setHomeButtonEnabled(homeAsUp);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public Activity getActivity() {
        return this;
    }

}
