/**
 * PlaceholderFragment.java
 * 
 * Created by zouyong on Jul 8, 2014,2014
 */
package com.chriszou.remember;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.TextView;

import com.chriszou.androidlibs.BaseViewBinderAdapter;
import com.chriszou.androidlibs.BaseViewBinderAdapter.ViewBinder;
import com.chriszou.androidlibs.L;
import com.chriszou.androidlibs.UrlContentLoader;

/**
 * @author zouyong
 *
 */
@EFragment
public class PlaceholderFragment extends Fragment implements UrlContentLoader.CallBack{
	private static final String TWEETS_LIST_URL = "http://112.124.121.155:3000/tweets";
	public PlaceholderFragment() {
	}

    @ViewById(R.id.main_webview)
    WebView mWebView;
    @ViewById(R.id.main_listview)
    ListView mListView;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		return rootView;
	}
    
	@AfterViews
	void initViews() {
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(TWEETS_LIST_URL);
        
        loadTweets();
	}
    
	private void loadTweets(){
        final String SERVER_URL = "http://112.124.121.155:3000/tweets.json";
		new UrlContentLoader(SERVER_URL).execute(this);
	}

	/* (non-Javadoc)
	 * @see com.chriszou.androidlibs.UrlContentLoader.CallBack#onSucceed(java.lang.String)
	 */
	@Override
	public void onSucceed(String content) {
       try {
			JSONArray array = new JSONArray(content);
            BaseViewBinderAdapter<JSONObject> adapter = new BaseViewBinderAdapter<JSONObject>(getActivity(), toJsonObjList(array), R.layout.tweet_item, new ViewBinder<JSONObject>() {
				@Override
				public void bindView(View view, JSONObject item, ViewGroup parent) {
					((TextView)view).setText(item.optString("content"));
				}
			}) ;
            mListView.setAdapter(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
    
	private List<JSONObject> toJsonObjList(JSONArray array) {
		List<JSONObject> objs = new ArrayList<JSONObject>();
        for(int i=0; i<array.length(); i++) {
            JSONObject obj = array.optJSONObject(i);
            if(obj!=null) {
            	objs.add(obj);
            }
        }
        
        return objs;
	}

	/* (non-Javadoc)
	 * @see com.chriszou.androidlibs.UrlContentLoader.CallBack#onFailed(java.lang.String)
	 */
	@Override
	public void onFailed(String msg) {
        L.l(msg);
	}

	/* (non-Javadoc)
	 * @see com.chriszou.androidlibs.UrlContentLoader.CallBack#onCanceld()
	 */
	@Override
	public void onCanceld() {
	}
}
