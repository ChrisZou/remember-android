package com.chriszou.remember.util;

import org.json.JSONObject;

/**
 * Created by Chris on 2/24/15.
 */
public class ErrorResponse extends NetworkResponse {
    public String errorMsg;

    public ErrorResponse(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    protected void resolveField() {
        super.resolveField();
        errorMsg = originalJson.optString("error_msg");
    }


}
