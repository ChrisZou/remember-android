package com.chriszou.remember.util;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Chris on 2/24/15.
 */
public class NetworkResponse {
    public boolean success;
    public String dataType;
    public JSONObject data;
    public JSONArray dataArray;

    public JSONObject originalJson;

    public NetworkResponse(JSONObject jsonObject) {
        originalJson = jsonObject;
        resolveField();
    }

    protected void resolveField() {
        success = originalJson.optBoolean("success");
        dataType = originalJson.optString("data_type");
        data = originalJson.optJSONObject("data");
        dataArray = originalJson.optJSONArray("data");
    }

}
