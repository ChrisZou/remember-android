/**
 * ShufferActivity.java
 * 
 * Created by zouyong on Aug 9, 2014,2014
 */
package com.chriszou.remember;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.chriszou.androidlibs.BaseViewBinderAdapter;
import com.chriszou.androidlibs.BaseViewBinderAdapter.ViewBinder;
import com.chriszou.androidlibs.L;
import com.chriszou.androidlibs.UrlContentLoader.Callback;
import com.chriszou.remember.model.TweetModel;

/**
 * @author zouyong
 *
 */
@EFragment(R.layout.shuffer_activity)
public class ReminderFragment extends Fragment implements Callback {
    
    public static final String EXTRA_STRING_REMINDER_TYPE = "extra_string_reminder_type";
    public static enum ReminderType{
    	SHUFFLE, TODAY;
    }
	@ViewById(R.id.shuffer_listview)
	ListView mListView;
    
	@AfterViews
	void loadData() {
		TweetModel model = new TweetModel();
		String cacheString = model.getTweetCache(getActivity());
		if (cacheString != null) {
			updateList(cacheString);
		}
		new TweetModel().loadTweets(this);
	}

	/* (non-Javadoc)
	 * @see com.chriszou.androidlibs.UrlContentLoader.CallBack#onSucceed(java.lang.String)
	 */
	@Override
	public void onSucceed(String content) {
		updateList(content);
		new TweetModel().saveCache(getActivity(), content);
	}

	private void updateList(String contentJson) {
		try {
			JSONArray array = new JSONArray(contentJson);
			List<JSONObject> tweetArray = getShowList(toJsonObjList(array));
			BaseViewBinderAdapter<JSONObject> adapter = new BaseViewBinderAdapter<JSONObject>(getActivity(), tweetArray,
					R.layout.tweet_item, new ViewBinder<JSONObject>() {
				@Override
				public void bindView(int position, View view, JSONObject item, ViewGroup parent) {
					TextView textView= (TextView)view;
					textView.setText(item.optString("content"));
                    int bgColor = position<5 ? Color.parseColor("#D6E1A4") : Color.TRANSPARENT;
                    textView.setBackgroundColor(bgColor);
				}
			}) ;
            mListView.setAdapter(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
    
	/**
	 * According to the reminder type, get the sublist to show in the UI
	 * @return 
	 */
	private List<JSONObject> getShowList(List<JSONObject> items) {
		String typeString = getArguments().getString(EXTRA_STRING_REMINDER_TYPE, ReminderType.SHUFFLE.name());
		ReminderType type = ReminderType.valueOf(typeString);
        List<JSONObject>result = null; 
		switch (type){
		case SHUFFLE:
            result = items;
            Collections.shuffle(result,new Random(System.nanoTime()));
            break;
		case TODAY:
			result = getTodayTweets(items);
		}
        
		return result;
	}
    
	/**
	 * Get the tweets that was created today
	 * 
	 * @param items
	 * @return
	 */
	private List<JSONObject> getTodayTweets(List<JSONObject> items) {
		List<JSONObject> result = new ArrayList<JSONObject>();
		String todayString = TimeHelper.getTodayString();
		for (JSONObject item : items) {
			String createdTime = item.optString("created_at");
			if (createdTime.startsWith(todayString)) {
				result.add(item);
			}
		}
		return result;
	}


	/* (non-Javadoc)
	 * @see com.chriszou.androidlibs.UrlContentLoader.CallBack#onFailed(java.lang.String)
	 */
	@Override
	public void onFailed(String msg) {
        L.l(msg);
	}
    
	/**
	 * Convert a JSONArray object to a list of JSONObject
	 * 
	 * @param array
	 * @return
	 */
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
	 * @see com.chriszou.androidlibs.UrlContentLoader.CallBack#onCanceld()
	 */
	@Override
	public void onCanceld() {
	}
    
}
