/**
 * PlaceholderFragment.java
 *
 * Created by zouyong on Jul 8, 2014,2014
 */
package com.chriszou.remember;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
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
@EFragment(R.layout.fragment_main)
public class MainFragment extends Fragment implements UrlContentLoader.Callback, ViewBinder<JSONObject> {
	public MainFragment() {
	}

	@ViewById(R.id.main_listview)
	ListView mListView;

	@ViewById(R.id.main_btn_add)
	Button mAddBtn;
	@ViewById(R.id.main_add_edit)
	EditText mAddEdit;

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

	private void addTweet(String text) {
		new TweetModel().addTweet(text);
	}

	@Background
	void loadTweets(){
		/*
		 * First load cache, then load from network.
		 */
		TweetModel model = new TweetModel();
		String cacheString = model.getTweetCache(getActivity());
		if(cacheString!=null) {
			updateList(cacheString);
		}
		model.loadTweets(this);
	}

	/* (non-Javadoc)
	 * @see com.chriszou.androidlibs.UrlContentLoader.CallBack#onSucceed(java.lang.String)
	 */
	@Override
	public void onSucceed(String content) {
		updateList(content);
		new TweetModel().saveCache(getActivity(), content);
	}

	@UiThread
	void updateList(String jsonContent) {
		try {
			JSONArray array = new JSONArray(jsonContent);
			List<JSONObject> tweetArray = toJsonObjList(array);
			BaseViewBinderAdapter<JSONObject> adapter = new BaseViewBinderAdapter<JSONObject>(getActivity(), tweetArray, R.layout.tweet_item, this);
			mListView.setAdapter(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

    /**
     * Converting an JSONArray object to a list of JSONObject
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

    @Override
    public void bindView(int position, View view, JSONObject item, ViewGroup parent) {
        ((TextView)view).setText(item.optString("content"));
    }
}
