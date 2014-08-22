/**
 * ShufferActivity.java
 * 
 * Created by zouyong on Aug 9, 2014,2014
 */
package com.chriszou.remember;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.chriszou.androidlibs.BaseViewBinderAdapter;
import com.chriszou.androidlibs.BaseViewBinderAdapter.ViewBinder;
import com.chriszou.androidlibs.L;
import com.chriszou.androidlibs.UrlContentLoader.CallBack;
import com.chriszou.remember.model.TweetModel;

/**
 * @author zouyong
 *
 */
@EActivity(R.layout.shuffer_activity)
public class ShufferActivity extends Activity implements CallBack{
    
    public static final String EXTRA_STRING_REMINDER_TYPE = "extra_string_reminder_type";
    public static enum ReminderType{
    	SHUFFLE, TODAY;
    }
	@ViewById(R.id.shuffer_listview)
	ListView mListView;
    
	@AfterViews
	void loadData() {
		new TweetModel().loadTweets(this);
	}

	/* (non-Javadoc)
	 * @see com.chriszou.androidlibs.UrlContentLoader.CallBack#onSucceed(java.lang.String)
	 */
	@Override
	public void onSucceed(String content) {
		try {
			JSONArray array = new JSONArray(content);
			List<JSONObject> tweetArray = getShowList(toJsonObjList(array));
            BaseViewBinderAdapter<JSONObject> adapter = new BaseViewBinderAdapter<JSONObject>(this, tweetArray, R.layout.tweet_item, new ViewBinder<JSONObject>() {
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
    
	/**
	 * According to the reminder type, get the sublist to show in the UI
	 * @return 
	 */
	private List<JSONObject> getShowList(List<JSONObject> items) {
		ReminderType type = ReminderType.valueOf(getIntent().getStringExtra(EXTRA_STRING_REMINDER_TYPE));
        List<JSONObject>result = null; 
		switch (type){
		case SHUFFLE:
            result = items;
            Collections.shuffle(result,new Random(System.nanoTime()));
            break;
		case TODAY:
            result = new ArrayList<JSONObject>();
            String todayString = getTodayString();
			for(JSONObject item: items){
				String createdTime = item.optString("created_at");
                if(createdTime.startsWith(todayString)) {
                	result.add(item);
                }
			}
		}
        
		return result;
	}
    
	private String getTodayString() {
		SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return sdfDateFormat.format(new Date());
	}

	/* (non-Javadoc)
	 * @see com.chriszou.androidlibs.UrlContentLoader.CallBack#onFailed(java.lang.String)
	 */
	@Override
	public void onFailed(String msg) {
        L.l(msg);
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
	 * @see com.chriszou.androidlibs.UrlContentLoader.CallBack#onCanceld()
	 */
	@Override
	public void onCanceld() {
	}
    
}
