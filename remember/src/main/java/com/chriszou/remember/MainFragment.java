/**
 * PlaceholderFragment.java
 *
 * Created by zouyong on Jul 8, 2014,2014
 */
package com.chriszou.remember;

import android.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.chriszou.androidlibs.Toaster;
import com.chriszou.androidlibs.ViewBinderAdapter;
import com.chriszou.androidlibs.ViewBinderAdapter.ViewBinder;
import com.chriszou.remember.model.Tweet;
import com.chriszou.remember.model.TweetModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zouyong
 *
 */
@EFragment(R.layout.fragment_main)
public class MainFragment extends TweetListFragment {
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
        try {
            new TweetModel().addTweet(new Tweet(text));
        } catch (IOException e) {
            Toaster.s(getActivity(), "Network connection failed");
        }
    }

    @Override
    protected ListView getListView() {
        return mListView;
    }

}
