/**
 *
 */
package com.chriszou.androidlibs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/**
 * @author Chris
 *
 */
public class UrlUtils {
	public static HttpResponse postJson(String url, String jsonString) throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "application/json");
		post.setEntity(new StringEntity(jsonString, "UTF-8"));
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 5 * 1000);
		HttpConnectionParams.setSoTimeout(params, 5 * 1000);
		DefaultHttpClient client = new DefaultHttpClient(params);
		HttpResponse response = client.execute(post);
		return response;
	}


    /**
     * Get the body from the HttpResponse and return as a String
     * @param response
     * @return
     * @throws IOException
     */
    public static String responseToString(HttpResponse response) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder builder = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null;) {
            builder.append(line).append("\n");
        }
        return builder.toString().trim();
    }
}
