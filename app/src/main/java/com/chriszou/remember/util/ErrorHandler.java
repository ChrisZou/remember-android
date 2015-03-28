package com.chriszou.remember.util;

import android.widget.Toast;

import com.chriszou.remember.R;
import com.chriszou.remember.RmbApplication;

/**
 * Created by Chris on 3/28/15.
 */
public class ErrorHandler {
    public static void onError(Throwable e) {
        String msg = e==null ? RmbApplication.getContext().getString(R.string.unknown_error) : e.getMessage();
        Toast.makeText(RmbApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
