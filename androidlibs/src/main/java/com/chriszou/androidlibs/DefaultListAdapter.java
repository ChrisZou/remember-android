/**
 * DefaultListAdapter.java
 * 
 * Created by zouyong on Aug 28, 2014,2014
 */
package com.chriszou.androidlibs;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * An Adapter that uses android.R.layout.simple_list_item1 as the item layout, if not otherwise specified, and shows one line of text
 * @author zouyong
 *
 */
public class DefaultListAdapter<E> extends BaseListAdapter<E> {

    private static final int INVALID_LAYOUT_ID = -1;
    private int mItemLayoutId = INVALID_LAYOUT_ID;
	/**
	 * @param context
	 * @param data
	 */
	public DefaultListAdapter(Context context, List<E> data) {
		super(context, data);
	}
    
	public void setItemLayout(int layoutResId)  {
		
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            int layoutId = (mItemLayoutId==INVALID_LAYOUT_ID) ? android.R.layout.simple_expandable_list_item_1 : mItemLayoutId;
        	convertView = mInflater.inflate(layoutId, parent, false);
        }
        TextView textView = (TextView)convertView;
        textView.setText(getItem(position).toString());
        return textView;
	}

}
