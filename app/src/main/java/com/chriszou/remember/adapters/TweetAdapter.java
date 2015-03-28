package com.chriszou.remember.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chriszou.remember.R;
import com.chriszou.remember.model.Tweet;
import com.chriszou.remember.model.TweetModel;
import com.chriszou.remember.util.ErrorHandler;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.util.List;

/**
 * Created by Chris on 3/28/15.
 */
public class TweetAdapter extends BaseSwipeAdapter{

    private final Activity mActivity;
    private final List<Tweet> mData;

    public TweetAdapter(Activity context, List<Tweet> tweets) {
        mActivity = context;
        mData = tweets;
    }

    @Override
    public int getSwipeLayoutResourceId(int i) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup viewGroup) {
        return LayoutInflater.from(mActivity).inflate(R.layout.tweet_item, viewGroup, false);
    }

    @Override
    public void fillValues(int i, View view) {
        final SwipeLayout layout = (SwipeLayout) view;
        TextView contentView = (TextView) view.findViewById(R.id.tweet_content);
        Tweet item = getItem(i);
        contentView.setText(item.getContent());
        view.findViewById(R.id.trash).setOnClickListener(v -> {confirmRemove(item); layout.close();});
    }

    private void confirmRemove(Tweet item) {
        ConfirmDialog.showAlertDialog(mActivity, mActivity.getString(R.string.confirm_delete), (dialog, which)->{removeItem(item); dialog.dismiss();});
    }

    private void removeItem(Tweet item) {
        TweetModel.getInstance().remove(item).subscribe(o->remove(item), ErrorHandler::onError);
    }

    public void remove(Tweet item) {
        mData.remove(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Tweet getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(int i, Tweet tweet) {
        mData.add(i, tweet);
        notifyDataSetChanged();
    }
}
