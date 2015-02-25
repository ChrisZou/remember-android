package com.chriszou.remember.util;

import com.android.volley.Request.Method;

/**
 * Created by Chris on 12/30/14.
 */
public class VolleyUtils {

    public static String getMethodName(int method) {
        String methodName = "Unknown Method";
        switch (method) {
        case Method.GET: methodName = "GET"; break;
        case Method.POST: methodName = "POST"; break;
        case Method.PATCH: methodName = "PATCH"; break;
        case Method.DELETE: methodName = "DELETE"; break;
        case Method.PUT: methodName = "PUT"; break;
        }
        return methodName;
    }

}
