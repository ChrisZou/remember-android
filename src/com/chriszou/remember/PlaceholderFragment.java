/**
 * PlaceholderFragment.java
 * 
 * Created by zouyong on Jul 8, 2014,2014
 */
package com.chriszou.remember;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.chriszou.androidlibs.BaseViewBinderAdapter;
import com.chriszou.androidlibs.BaseViewBinderAdapter.ViewBinder;
import com.chriszou.androidlibs.L;
import com.chriszou.androidlibs.UrlContentLoader;
import com.chriszou.remember.model.TweetModel;

/**
 * @author zouyong
 *
 */
@EFragment
public class PlaceholderFragment extends Fragment implements UrlContentLoader.CallBack{
	public PlaceholderFragment() {
	}

    @ViewById(R.id.main_listview)
    ListView mListView;
    
    @ViewById(R.id.main_btn_add)
    Button mAddBtn;
    @ViewById(R.id.main_add_edit)
    EditText mAddEdit;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		return rootView;
	}
    
	@AfterViews
	void initViews() {
        loadTweets();
	}
    
    @Click(R.id.main_btn_add)
    void onAddClicked() {
        String text = mAddEdit.getText().toString().trim();
        if(text.length()==0) {
        	return;
        }
        
        addTweet(text);
    }
    
    @Click(R.id.main_shuffer)
    void onShuffer() {
    	Intent intetn = new Intent(getActivity(), ShufferActivity_.class);
    	startActivity(intetn);
    }
    
    
    private void addTweet(String text) {
    	new TweetModel().addTweet(text);
    }
   
	private void loadTweets(){
        new TweetModel().loadTweets(this);
	}

	/* (non-Javadoc)
	 * @see com.chriszou.androidlibs.UrlContentLoader.CallBack#onSucceed(java.lang.String)
	 */
	@Override
	public void onSucceed(String content) {
       try {
			JSONArray array = new JSONArray(content);
            List<JSONObject> tweetArray = toJsonObjList(array);
            BaseViewBinderAdapter<JSONObject> adapter = new BaseViewBinderAdapter<JSONObject>(getActivity(), tweetArray, R.layout.tweet_item, new ViewBinder<JSONObject>() {
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
