/**
 * PlaceholderFragment.java
 * 
 * Created by zouyong on Jul 8, 2014,2014
 */
package com.chriszou.remember;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chriszou.androidlibs.L;

/**
 * @author zouyong
 *
 */
@EFragment
public class PlaceholderFragment extends Fragment{
	private static final String TWEETS_LIST_URL = "http://112.124.121.155:3000/tweets";
	public PlaceholderFragment() {
	}

//    @ViewById(R.id.main_webview)
    WebView mWebView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mWebView = (WebView) rootView.findViewById(R.id.main_webview);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(TWEETS_LIST_URL);
//        mWebView.loadUrl("http://www.baidu.com");
		return rootView;
	}
    
//	@AfterViews
//	void initViews() {
//        L.l("load url");
//		mWebView.loadUrl("http://www.baidu.com");
////        mWebView.loadUrl(TWEETS_LIST_URL);
//	}
}
